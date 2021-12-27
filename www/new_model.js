function registerModel() {
    var model = document.getElementById("modelNumber").value;
    var name = document.getElementById("name").value;
    var manual = document.getElementById("manual").value;
    var ret = document.getElementById("result");
    if(Object.keys(model).length === 0 || Object.keys(name).length === 0) {
        ret.innerHTML = "Fields marked with * are mandatory.";
        ret.style.color = "red";
        return;
    }
    if(Object.keys(manual).length === 0) {
        var obj = { "modelNumber" : model,
            "name" : name,
            "device" : "TV",
            "brand" : "Samsung"
        }
    } else {
        var obj = { "modelNumber" : model,
            "name" : name,
            "device" : "TV",
            "brand" : "Samsung",
            "manual" : manual
        }
    }


    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if (this.readyState == 4) {
            if(this.status == 201) {
                ret.innerHTML = "<p>Success</p>";
                ret.style.color = "green";
                setTimeout(function () {
                    window.open("http://localhost:8080/", "_self");
                }, 3000);
            } else if (this.status == 409) {
                ret.innerHTML = "<p>" + this.responseText + "</p>";
                ret.style.color = "red";
            }
        }

    }

    request.ontimeout = function() {
        ret.innerHTML = "Server timeout";
        ret.style.color="red";
    };

    request.onerror = function() {
        ret.innerHTML = "Server not available";
        ret.style.color="red";
    };

    //fetch("parts", {method: "POST", headers: {'Content-Type' : 'application/json'}, body: JSON.stringify(obj)});
    request.open("POST", "/models", true);

    request.timeout = 15000;
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(obj));



}