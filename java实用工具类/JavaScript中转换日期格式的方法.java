
{ 一、JavaScript中转换日期格式的方法

var date = new Date(value);  
        var year = date.getFullYear().toString();  
        var month = (date.getMonth() + 1);  
        var day = date.getDate().toString();  
        var hour = date.getHours().toString();  
        var minutes = date.getMinutes().toString();  
        var seconds = date.getSeconds().toString();  
        if (month < 10) {  
            month = "0" + month;  
        }  
        if (day < 10) {  
            day = "0" + day;  
        }  
        if (hour < 10) {  
            hour = "0" + hour;  
        }  
        if (minutes < 10) {  
            minutes = "0" + minutes;  
        }  
        if (seconds < 10) {  
            seconds = "0" + seconds;  
        }  
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;  //可以根据具体需要什么样的日期格式来设置拼接返回的时间的格式

}

