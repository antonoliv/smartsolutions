function registerPart() {
    var partNumber = document.getElementById("partNumber").value;
    var name = document.getElementById("name").value;
    var model = document.getElementById("modelNumber").value;
    var stock = document.getElementById("stock").value;
    var pics = document.getElementById("pictures").files;
    var ret = document.getElementById("state");
    if(Object.keys(partNumber).length === 0 || Object.keys(name).length === 0 || Object.keys(model).length === 0 || Object.keys(stock).length === 0) {
        ret.innerHTML = "Fields marked with * are mandatory.";
        ret.style.color = "red";
        return;
    }

    var pictures = [];

    for(let i = 0; i < pics.length; i++) {

        var reader = new FileReader();
        reader.readAsDataURL(pics[i]);

        reader.onload = function(e) {
            if(e.target.result.startsWith("data:image")) {

                console.log(e.target.result);
                pictures[i] = e.target.result;
            } else {
                ret.innerHTML = "Only Image formats are accepted.";
                ret.style.color = "red";
                return;
            }
        }
    }



    var obj = { "part_number" : partNumber,
        "name" : name,
        "brand" : "Samsung",
        "model_number" :  model,
        "stock" : stock,
        "pictures" : pictures,
        };

    console.log(obj);

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
    request.open("POST", "/parts", true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(obj));
}

function getModels() {
    var props = document.getElementById("compatible");
    var model = document.getElementById("modelNumber");
    var request = new XMLHttpRequest();
    request.open("GET", "/models", true);
    request.timeout = 15000;
    request.send();

    request.onreadystatechange = function() {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var json = JSON.parse(this.responseText);
                for (let i = 0; i < json.models.length; i++) {
                    let opt1 = document.createElement("option");
                    let opt2 = document.createElement("option");
                    opt1.appendChild(document.createTextNode(json.models[i].model_number));
                    opt2.appendChild(document.createTextNode(json.models[i].model_number));
                    model.appendChild(opt1);
                    props.appendChild(opt2);
                }
            }

        }
    }
}

function addCompatible() {

    var props = document.getElementById("compatible");
    var cmps = document.getElementById("comps");
    var ps = cmps.getElementsByTagName("li");

    for(let i = 0; i < ps.length; i++) {
        let input = ps.item(i).getElementsByTagName("input")[0];

        if(props.value === input.value) {
            return;
        }
    }
    var li = document.createElement("li");
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("readonçy", "")
    input.value = props.value;
    li.appendChild(input);
    cmps.appendChild(li);
}