<!DOCTYPE html>
<html lang="ko">
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	RESTful API Test
	<br/>
	val1: <input type="text" id="ui_val1" /><br/>
	val2: <input type="text" id="ui_val2" /><br/>
	<button style="width:100px" id="ui_btnAdd">Add</button>
	<button style="width:100px" id="ui_btnDiv">Div</button>
	<br/>
	<br/>
	<span id="ui_result"></span>
</body>
<script type='text/javascript'>
$(document).ready(function(){
	console.log('RESTful API test page.');


	//  Add button click event
	$('#ui_btnAdd').on('click', function() {
		console.log('Add button clicked.');

        $.ajax({
            type: 'GET',
            url: '/api/add',
            data: {
                val1: $('#ui_val1').val(),
                val2: $('#ui_val2').val()
            },
            cache: false,
            success: function (result) {
                $('#ui_result').html('Result: ' + result);
            },
            error: function (xhr, ajaxOptions, thrownError) {
            }
        });
	});


	//  Div button click event
	$('#ui_btnDiv').on('click', function() {
		console.log('Div button clicked.');

        $.ajax({
            type: 'GET',
            url: '/api/div',
            data: {
                val1: $('#ui_val1').val(),
                val2: $('#ui_val2').val()
            },
            cache: false,
            success: function (result) {
                $('#ui_result').html('Result: ' + result);
            },
            error: function (xhr, ajaxOptions, thrownError) {
            }
        });
	});
});
</script>
</html>
