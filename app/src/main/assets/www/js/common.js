var Common = {
    /**
     * APP으로 Data 전송
     * @param {string} param 
     */
    sendDataToApp: function(param) {
        
        // Test
        $("#sendData").text(param);

        var param = param;
        var callbackFunc = "Common.receiveDataFromAppJsonParam";
        // Send App
        //window.location.href = "gsepartner://sampleFuntion?" + param + "&callback=" + callbackFunc;

        var obj = new Object();
        obj.callback = callbackFunc;
        var jsonData = JSON.stringify(obj);

        $.ajax({
          type: 'POST',
          url: "gsepartner://sampleFuntion",
          dataType: 'text/html',
          data: jsonData,
          success: function(data){
                    console.log('성공');
                   }
        })

    },
    /**
     * APP에서 Json Data를 받는다.
     * @param {json string} param 
     */
    receiveDataFromAppJsonParam: function(param) {
        console.log("receiveDataFromAppJsonParam : " + param);

        var jsonParam = JSON.stringify(param);


        // Test
        $("#recevieData").text(jsonParam);
    }
}