function getModel() {

    var request = new XMLHttpRequest();
    var urlParams = new URLSearchParams(document.location.search);
    var id = urlParams.get("id");
    var ret = document.getElementById("state");

    request.onreadystatechange = function() {
        if (this.readyState === 4) {
            if(this.status === 200) {
                updateModel(request.responseText);
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
    request.open("GET", "http://localhost:8080/models/" + id, true);
    request.timeout = 15000;
    request.send();
}

function updateModel(response) {
    var name = document.getElementById("name");
    var model = document.getElementById("modelNumber");
    var brand = document.getElementById("brand");
    var type = document.getElementById("type");
    var props = document.getElementById("properties");
    var p = JSON.parse(response);
    console.log(p);
    document.title = p.name;
    document.getElementById("title").innerText = p.name;
    name.value = p.name;
    model.value = p.model_number;
    brand.value = p.brand;
    type.value = p.type;
    updateProperties();
    for (let i = 0; i < p.properties.length; i++) {
        let list = document.createElement("li");
        let label = document.createElement("label");
        label.setAttribute("for", p.properties[i].name);
        label.appendChild(document.createTextNode(p.properties[i].name + ": "))
        let input = document.createElement("input");
        input.setAttribute("type", "text");
        input.setAttribute("name", p.properties[i].name);
        input.setAttribute("id", p.properties[i].name);
        input.setAttribute("readonly", "");

        input.value = p.properties[i].value;
        list.appendChild(label);
        list.appendChild(input);
        props.appendChild(list);

    }
}

function updateProperties() {

    var props = document.getElementById("properties");
    var request = new XMLHttpRequest();
    request.open("GET", "/properties", true);
    request.timeout = 15000;
    request.send();

    request.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var json = JSON.parse(this.responseText);

            }

        }
    }
}