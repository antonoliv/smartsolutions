function searchPart() {
    var model = document.getElementById("modelNumber").value;
    var part = document.getElementById("partNumber").value;
    var name = document.getElementById("name").value;
    var ret = document.getElementById("result");
    var url = new URL("http://localhost:8080/search_part");
    if(Object.keys(name).length !== 0) {
        url.searchParams.set("name", name);
    }
    if(Object.keys(part).length !== 0) {
        url.searchParams.set("part", part);
    }
    if(Object.keys(model).length !== 0) {
        url.searchParams.set("model", model);
    }

    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if (this.readyState === 4) {
            if(this.status === 200) {


            } else if (this.status === 400) {
                ret.innerHTML = "<p>" + this.responseText + "</p>";
                ret.style.color = "red";
            }
        }

    };

    request.ontimeout = function() {
        ret.innerHTML = "Server timeout";
        ret.style.color="red";
    };

    request.onerror = function() {
        ret.innerHTML = "Server not available";
        ret.style.color="red";
    };

    //fetch("parts", {method: "POST", headers: {'Content-Type' : 'application/json'}, body: JSON.stringify(obj)});
    request.open("GET", url, true);

    request.timeout = 15000;
    request.send();
}