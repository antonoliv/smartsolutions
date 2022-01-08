function registerModel() {
    var model = document.getElementById("modelNumber").value;
    var name = document.getElementById("name").value;
    var manual = document.getElementById("manual").value;
    var propsl = document.getElementById("properties");
    var props = propsl.getElementsByTagName("li");

    var ret = document.getElementById("state");
    if(Object.keys(model).length === 0 || Object.keys(name).length === 0) {
        ret.innerHTML = "Fields marked with * are mandatory.";
        ret.style.color = "red";
        return;
    }


        var obj = new Object();
        obj.model_number = model;
        obj.name = name;
        obj.type = "TV";
        obj.brand = "Samsung";
        if(Object.keys(manual).length !== 0) {
            obj.manual = manual;
        }
        obj.properties = [props.length];


    for(let i = 0; i < props.length; i++) {
        let input = props.item(i).getElementsByTagName("input")[0];
        obj.properties[i] = new Object();
        obj.properties[i].name = input.getAttribute("name");
        obj.properties[i].value = input.value;
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

function getProperties() {



    var props = document.getElementById("property");
    var request = new XMLHttpRequest();
    request.open("GET", "/properties", true);
    request.timeout = 15000;
    request.send();

    request.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var json = JSON.parse(this.responseText);
                for (let i = 0; i < json.properties.length; i++) {
                    let opt = document.createElement("option");
                    opt.appendChild(document.createTextNode(json.properties[i].name));
                    props.appendChild(opt);
                }
            }

        }
    }
}

function addProperty() {



    var sel = document.getElementById("property").value;
    var props = document.getElementById("properties");
    var ps = props.getElementsByTagName("li");

    for(let i = 0; i < ps.length; i++) {
        let input = ps.item(i).getElementsByTagName("input")[0];

        if(sel === input.getAttribute("name")) {
            return;
        }
    }


    var line = document.createElement("li");

    var label = document.createElement("label");
    label.setAttribute("for", sel);
    label.appendChild(document.createTextNode(sel + ": "));

    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", sel);
    input.setAttribute("id", sel);

    var button = document.createElement("button");
    button.setAttribute("type", "button");
    button.appendChild(document.createTextNode("-"));
    button.addEventListener("click", function () {
        line.remove();
    })

    line.appendChild(label);
    line.appendChild(input);
    line.appendChild(button);

    props.appendChild(line);
}