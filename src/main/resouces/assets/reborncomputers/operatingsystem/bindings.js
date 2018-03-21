var computerBindings = Java.type('me.modmuss50.reborncomputers.computer.bindings.ComputerBind');

function print(data){
    computerBindings.print(data);
}

function printLn(data){
    computerBindings.print(data);
}

function monitor_clear(){
    computerBindings.clear();
}

function monitor_set(char, x, y) {
    computerBindings.setPoint(char, x, y);
}

function monitor_height() {
    return computerBindings.getHeight();
}

function monitor_width() {
    return computerBindings.getWidth();
}