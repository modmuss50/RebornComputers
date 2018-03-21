
function version(){
    return "0.0.1"
}

function init(id) {
    monitor_clear();
    printLn("Starting RebornOS " + version() + " on " + id);
    printLn("Current screen res " + monitor_width() + "x" + monitor_height())

    // while(true){
    //     print(id)
    // }
}