function modificarCuenta() {
    var nuevocorreo = document.getElementById('mail').value;    
    var nuevaclave = document.getElementById('clave').value;
    url = "CuentaCtrl?op=modificarCuentaPropia";
    url = url + "&email=" + nuevocorreo + "&clave=" + nuevaclave;
    ai = new AJAXInteraction(url, resultadoModificarCuenta, "Text");
    ai.doGet();
}

function resultadoModificarCuenta(resultado) {
    document.getElementById("resultado").innerHTML = resultado;    
}