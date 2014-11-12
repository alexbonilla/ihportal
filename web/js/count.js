var Script = function() {
    buscarIngresos();
    buscarGastos();
}();

function buscarIngresos() {
    url = "TramiteCtrl?op=buscarTramitesHoy";
    ai = new AJAXInteraction(url, cargarIngresos, "XML");
    ai.doGet();

}
function cargarIngresos(result) {

    var tramites = result.getElementsByTagName('tramites')[0];
    var subtotalTramites = tramites.getElementsByTagName('subtotalTramites')[0].firstChild.nodeValue;
    var ivaTramites = tramites.getElementsByTagName('ivaTramites')[0].firstChild.nodeValue;
    var totalTramites = tramites.getElementsByTagName('totalTramites')[0].firstChild.nodeValue;
    var totaladicionalTramites = tramites.getElementsByTagName('totaladicionalTramites')[0].firstChild.nodeValue;
    var abonosTramites = tramites.getElementsByTagName('abonosTramites')[0].firstChild.nodeValue;


    $display = $('.count');
    $display.text(subtotalTramites);
    $display = $('.count3');
    $display.text(abonosTramites);
    $display = $('.count2');
    $display.text(totaladicionalTramites);

}

function buscarGastos() {
    url = "GastoCtrl?op=buscarGastosHoy";
    ai = new AJAXInteraction(url, cargarGastos, "XML");
    ai.doGet();

}

function cargarGastos(result) {
    var gastos = result.getElementsByTagName('gastos')[0];
    var totalGastos = gastos.getElementsByTagName('totalGastos')[0].firstChild.nodeValue;
    $display = $('.count4');
    $display.text(totalGastos);    
}


function roundNumber(number, decimals) { // Arguments: number to round, number of decimal places
    var newnumber = new Number(number + '').toFixed(parseInt(decimals));
    return  parseFloat(newnumber); // Output the result to the form field (change for your purposes)
}


