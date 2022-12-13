var contentTypeStr = 'application/json; charset=utf-8';
var dataTableData = [];
var userIdx = 0;
var nowPoint = 0;
var userId = '';

var initDataTable = function(){
	$("#input-point").val('');
	$('#data-table').DataTable( {
		destroy:true
	    , searching: false
	    , data: dataTableData
	    , columns: [
	        { data: 'STATUS' }
	        , { data: 'POINT' }
	        , { data: 'CREATE_DTTM' }
	        , { data: 'UPDATE_DTTM' }
	        , { data: 'EXPIRATION_DTTM', "defaultContent": "-"}
	    ]
	});
}


/* user_list 조회 */
var selectUserList = function() {
	$.ajax({
		url: "/user/user-list"
		, type : "GET"
		, contentType : contentTypeStr
		, success: function (response) {   
			var userJsonArray = response;
			for(var i=0; i<userJsonArray.length; i++) {
				var jsonObject = JSON.parse(JSON.stringify(userJsonArray[i]));
				var userIdx = jsonObject["IDX"];
				var userId = jsonObject["USER_ID"];
				
				$("#user-list").append("<a class='collapse-item' data-value='" + userIdx +"' onclick='selectPointEvent("+JSON.stringify(userJsonArray[i])+");' >"+ userId +"</a>");
			}
        }, error: function (request) {
        	console.log("ERROR : " + request );  
        }
     });
     
     
}


/* point_event 조회 */
var selectPointEvent = function(jsonObject) {
	var oThis = this;

	this.userIdx = jsonObject["IDX"];
	this.userId = jsonObject["USER_ID"];
	

	$.ajax({
		url: "/point/point-event/userIdx=" + userIdx
		, type : "GET"
		, contentType : contentTypeStr
		, success: function (response) {   
			oThis.dataTableData = response;
			console.log(oThis.dataTableData);
			
			/* table 초기화 */
			initDataTable();
			
			/* point_summary 조회 */
			oThis.nowPoint = selectPointSummary(userIdx);
			
			/* 소제목 변경 */
			console.log(response[0])
			$('#sub-title').text(userId + "의 적립금 사용 내역 ( 잔여 적립금 : " + oThis.nowPoint + " )");
	    }, error: function (request) {
	    	console.log("ERROR : " + request );  
	    }
	});
}


/* point_summary 조회 */
var selectPointSummary = function(userIdx) {
	var nowPoint = 0;
	$.ajax({
		url: "/point/point-summary/userIdx=" + userIdx
		, type : "GET"
		, contentType : contentTypeStr
		, async : false
		, success: function (response) {   
			var summaryObject = JSON.parse(JSON.stringify(response[0]));
			nowPoint = summaryObject['NOW_POINT'];
	    }, error: function (request) {
	    	console.log("ERROR : " + request );  
	    }
	});
	
	return nowPoint;
}


var doJobPointEvent = function(status) {
	
	var oThis = this;
	
	if(userIdx == 0){
		alert("사용자를 선택해 주세요");
		return
	}

	var targetPoint = (status == "use") ? -$("#input-point").val() : $("#input-point").val();
	var param = {};
	
	
	if( (Number(nowPoint) + Number(targetPoint)) < 0) {
		alert("적립금 사용 금액을 확인해주세요");
		return		
	}
		
	
	param["userIdx"] = this.userIdx;
	param["status"] = status;
	param["point"] = targetPoint; 

	$.ajax({
		url: "/point/point-event"
		, type : "POST"
		, data :  JSON.stringify(param)
		, dataType : "json"
		, contentType : contentTypeStr
		, success: function (response) {   
			oThis.dataTableData = response;
			console.log(oThis.dataTableData);
			
			/* table 초기화 */
			initDataTable();
			
			/* point_summary 조회 */
			oThis.nowPoint = selectPointSummary(userIdx);
			
			/* 소제목 변경 */
			console.log(response[0])
			$('#sub-title').text(userId + "의 적립금 사용 내역 ( 잔여 적립금 : " + oThis.nowPoint + " )");
			
	    }, error: function ( error) {
	    	console.log(error);  
	    }
	});
}

$(function() {
    console.log( "documnet ready start !!" );
    selectUserList();
    initDataTable();
});
