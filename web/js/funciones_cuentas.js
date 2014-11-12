var Script = function() {
    obtenerSigNumFactura();
}();

function obtenerSigNumFactura(){
    var url = "FacturaCtrl?op=numFactura";
    var ai = new AJAXInteraction(url, cargarSigNumFactura, "Text");
    ai.doGet();
}

function cargarSigNumFactura(resultado) {
    document.getElementById("numerocomprobante").value= resultado;
}

function procesarFactura() {
    
    var tipoidcomprador = document.getElementById('tipoidcomprador').value;
    var idcomprador = document.getElementById('idcomprador').value;
    var razonsocial = document.getElementById('razonsocial').value;
    var operador = document.getElementById('operador').value;
    var email = document.getElementById('email').value;
    var codproducto = document.getElementById('codproducto').value;
    var precio = document.getElementById('precio').value;
    var codestab = document.getElementById('codestab').value;
    var ptoemi = document.getElementById('ptoemi').value;
    var numerocomprobante = document.getElementById('numerocomprobante').value;    
    var url = "FacturaCtrl?op=procesoCompleto&tipoidcomprador="+tipoidcomprador+"&idcomprador="+idcomprador+"&razonsocial="+razonsocial+"&operador="+operador+"&email="+email+"&codproducto="+codproducto+"&precio="+precio+"&codestab="+codestab+"&ptoemi="+ptoemi+"&numerocomprobante="+numerocomprobante;
    cargar_animacion('contenedor');    
    var ai = new AJAXInteraction(url, resultadoProcesarFactura, "Text");
    ai.doGet();
}

function resultadoProcesarFactura(resultado) {
    alert(resultado);
    var contenedor	= document.getElementById('contenedor');
    var html = "<div align='center'><a href='old_operations.html' class='btn btn-info'>Nueva Factura</a></div>";
    contenedor.innerHTML = html;
}

function procesarFacturasTrcRUC() {
    
    var registro_resultado_inicial = document.getElementById('registro_resultado_inicial').value;
    var numero_registros_procesar = document.getElementById('numero_registros_procesar').value;
    var url = "FacturaCtrl?op=procesoBatch&registro_resultado_inicial="+registro_resultado_inicial+"&numero_registros_procesar="+numero_registros_procesar;
    cargar_animacion('contenedorBatch');    
    var ai = new AJAXInteraction(url, resultadoProcesarFacturasTrcRUC, "Text");
    ai.doGet();
}

function resultadoProcesarFacturasTrcRUC(resultado) {
//    alert(resultado);
    var contenedor	= document.getElementById('contenedorBatch');
    var html = "<div align='center'>" + resultado + "</div>";
    html += "<div align='center'><a href='index.html' class='btn btn-info'>Nuevo Proceso</a></div>";
    contenedor.innerHTML = html;
}

function cargar_animacion(idfr){
    var contenedor	= document.getElementById(idfr);
    var html 	= "<div align='center'><img src='img/loading_1.gif' /></div>";
    contenedor.innerHTML = html;
}

