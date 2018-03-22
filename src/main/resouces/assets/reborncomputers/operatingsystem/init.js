
function version(){
    return "0.0.1"
}

function init(id) {
    monitor_clear();
    printLn("Starting RebornOS " + version() + " on " + id);
    printLn("Current screen res " + monitor_width() + "x" + monitor_height());

    var exit = false;

  //  while (!exit){
        printLn(">");
        var input = inputLine();
        printLn("Echo: " + input);

        if(input === "exit"){
            exit = true;
        }
    //}

    //printLn("Goodbye o/")

}