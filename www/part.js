function getPart() {

    var request = new XMLHttpRequest();
    var urlParams = new URLSearchParams(document.location.search);
    var id = urlParams.get("id");
    var ret = document.getElementById("state");

    var code = document.getElementById("code");
    code.value = id;

    request.onreadystatechange = function() {
        if (this.readyState === 4) {
            if(this.status === 200) {
                updatePart(request.responseText);
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

    request.open("GET", "http://localhost:8080/parts/" + id, true);
    request.timeout = 15000;
    request.send();
}

function updatePart(response) {
    var name = document.getElementById("name");
    var stock = document.getElementById("stock");
    var part = document.getElementById("partNumber");
    var model = document.getElementById("modelNumber");
    var brand = document.getElementById("brand");
    var type = document.getElementById("type");
    var p = JSON.parse(response);
    document.title = p.name;
    document.getElementById("title").innerText = p.name;
    name.value = p.name;
    part.value = p.part_number;
    model.value = p.model_number;
    brand.value = p.brand;
    type.value = p.device_type;
    stock.value = p.stock;
}