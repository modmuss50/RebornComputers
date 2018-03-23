init(123);

function version(){
    return "0.0.1"
}

function init(id) {
    var bindings = require("bindings.js");
    getMethods(bindings).join("\n");
    bindings.Monitor_clear();
    bindings.printLn("Starting RebornOS " + version() + " on " + id);
    bindings.printLn("Current screen res " + monitor_width() + "x" + monitor_height());
    bindings.printLn(Applications.length + " Loaded applications");
    comandInput();
}

function comandInput(){
    print(">");

    var input = bindings.inputLine();
    printLn("");
    if(!isBlank(input)){
        var app = bindings.findApplication(input);
        if(app == null){
            printLn("Command " + input + " not found!");
        } else {
            app.func()
        }
    }
    comandInput();
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function getMethods(obj) {
    var result = [];
    for (var id in obj) {
        try {
            if (typeof(obj[id]) == "function") {
                result.push(id + ": " + obj[id].toString());
            }
        } catch (err) {
            result.push(id + ": inaccessible");
        }
    }
    return result;
}