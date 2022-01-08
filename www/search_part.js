function searchPart() {
    var model = document.getElementById("modelNumber").value;
    var part = document.getElementById("partNumber").value;
    var name = document.getElementById("name").value;

    var propsl = document.getElementById("properties");
    var props = propsl.getElementsByTagName("li");

    var ret = document.getElementById("state");
    var url = new URL("http://localhost:8080/search_part");

    // if(Object.keys(name).length !== 0) {
    //     url.searchParams.set("name", name);
    // }
    // if(Object.keys(part).length !== 0) {
    //     url.searchParams.set("part", part);
    // }
    // if(Object.keys(model).length !== 0) {
    //     url.searchParams.set("model", model);
    // }

    var obj = new Object();
    obj.name = name;
    obj.model_number = model;
    obj.part_number = part;

    obj.properties = [props.length];

    for(let i = 0; i < props.length; i++) {
        let input = props.item(i).getElementsByTagName("input")[0];
        while(input.value.trim().length === 0) {
            i++;
            let input = props.item(i).getElementsByTagName("input")[0];
        }
        obj.properties[i] = new Object();
        obj.properties[i].name = input.getAttribute("name");
        obj.properties[i].value = input.value;
    }

    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if (this.readyState === 4) {
            if(this.status === 200) {

                //document.getElementById("search").innerHTML = request.responseText;
                drawResults(request.responseText);
            } else if (this.status === 400) {
                ret.innerHTML = "<p>" + this.responseText + "</p>";
                ret.style.color = "red";
            }
        }

    };
console.log(obj);
    request.ontimeout = function() {
        ret.innerHTML = "Server timeout";
        ret.style.color="red";
    };

    request.onerror = function() {
        ret.innerHTML = "Server not available";
        ret.style.color="red";
    };

    //fetch("parts", {method: "POST", headers: {'Content-Type' : 'application/json'}, body: JSON.stringify(obj)});
    request.open("POST", url, true);

    request.timeout = 15000;
    request.send(JSON.stringify(obj));
}

function drawResults(str_results) {
    var search = document.getElementById("search");
    if(document.body.contains(document.getElementById("results"))) {
       document.getElementById("results").remove();
    }
    var table = document.createElement("table");
    table.setAttribute("id", "results");
    console.log(str_results);
    var results = JSON.parse(str_results);
    var thead = table.createTHead();
    var row = thead.insertRow();
    var th1 = row.appendChild(document.createElement("th"));
    var th2 = row.appendChild(document.createElement("th"));
    var th3 = row.appendChild(document.createElement("th"));
    var th4 = row.appendChild(document.createElement("th"));
    var th5 = row.appendChild(document.createElement("th"));
    var th6 = row.appendChild(document.createElement("th"));
    th1.setAttribute("scope", "col");
    th2.setAttribute("scope", "col");
    th3.setAttribute("scope", "col");
    th4.setAttribute("scope", "col");
    th5.setAttribute("scope", "col");
    th6.setAttribute("scope", "col");
    th1.appendChild(document.createTextNode("Name"));
    th2.appendChild(document.createTextNode("Stock"));
    th3.appendChild(document.createTextNode("Part Number"));
    th4.appendChild(document.createTextNode("Model Number"));
    th5.appendChild(document.createTextNode("Brand"));
    th6.appendChild(document.createTextNode("Device"));

    if(results.search_results === undefined) {

        search.appendChild(table);
        return;
    }

    for(let i = 0; i < results.search_results.length; i++) {
        let part = results.search_results[i];
        let row = table.insertRow();
        let name = row.insertCell();
        let stock = row.insertCell();
        let part_number = row.insertCell();
        let model_number = row.insertCell();
        let brand = row.insertCell();
        let device = row.insertCell();
            linkn = document.createElement("a");
            linkn.setAttribute("href", "http://localhost:8080/part.html?id=" + results.search_results[i].id);
            linkn.appendChild(document.createTextNode(part.name));
            name.appendChild(linkn);
            part_number.appendChild(document.createTextNode(part.part_number));
            linkm = document.createElement("a");
            linkm.setAttribute("href", "http://localhost:8080/model.html?id=" + results.search_results[i].model_number);
            linkm.appendChild(document.createTextNode(part.model_number))
            model_number.appendChild(linkm);
            brand.appendChild(document.createTextNode(part.brand));
            device.appendChild(document.createTextNode(part.device_type));
            stock.appendChild(document.createTextNode(part.stock));

    }
    search.appendChild(table);
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