function version(){
    return "0.0.1"
}

function init(id) {
    monitor_clear();
    printLn("Starting RebornOS " + version() + " on " + id);
    printLn("Current screen res " + monitor_width() + "x" + monitor_height());
    printLn(Applications.length + " Loaded applications");
    comandInput();
}

function comandInput(){
    print(">");

    var input = inputLine();
    printLn("");
    if(!isBlank(input)){
        var app = findApplication(input);
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