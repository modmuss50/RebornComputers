var computerBindings = Packages.me.modmuss50.reborncomputers.computer.bindings.ComputerBind;

export function Print(data){
    computerBindings.print(data);
}

export function printLn(data){
    computerBindings.printLn(data);
}

export function Monitor_clear(){
    computerBindings.clear();
}

export function monitor_set(char, x, y) {
    computerBindings.setPoint(char, x, y);
}

export function monitor_height() {
    return computerBindings.getHeight();
}

export function monitor_width() {
    return computerBindings.getWidth();
}

export function inputLine(){
    return computerBindings.inputLine();
}