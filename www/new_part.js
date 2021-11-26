function registerPart() {
    var partNumber = document.getElementById("partNumber").value;
    var name = document.getElementById("name").value;
    var model = document.getElementById("modelNumber").value;
    var obj = { "partNumber" : partNumber,
        "name" : name,
        "brand" : "Phillips",
        "modelNumber" :  model,
        };

    var request = new XMLHttpRequest();
    //fetch("parts", {method: "POST", headers: {'Content-Type' : 'application/json'}, body: JSON.stringify(obj)});
    request.open("POST", "/parts", true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(obj));
}