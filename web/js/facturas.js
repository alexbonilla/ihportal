//date picker start

if (top.location != location) {
    top.location.href = document.location.href;
}
$(function() {
    window.prettyPrint && prettyPrint();
    $('.default-date-picker').datepicker({
        format: 'mm-dd-yyyy'
    });
    $('.dpYears').datepicker();
    $('.dpMonths').datepicker();


    var startDate = new Date(2012, 1, 20);
    var endDate = new Date(2012, 1, 25);
    $('.dp4').datepicker()
            .on('changeDate', function(ev) {
                if (ev.date.valueOf() > endDate.valueOf()) {
                    $('.alert').show().find('strong').text('The start date can not be greater then the end date');
                } else {
                    $('.alert').hide();
                    startDate = new Date(ev.date);
                    $('#startDate').text($('.dp4').data('date'));
                }
                $('.dp4').datepicker('hide');
            });
    $('.dp5').datepicker()
            .on('changeDate', function(ev) {
                if (ev.date.valueOf() < startDate.valueOf()) {
                    $('.alert').show().find('strong').text('The end date can not be less then the start date');
                } else {
                    $('.alert').hide();
                    endDate = new Date(ev.date);
                    $('.endDate').text($('.dp5').data('date'));
                }
                $('.dp5').datepicker('hide');
            });

    // disabling dates
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var checkin = $('.dpd1').datepicker({
        onRender: function(date) {
            return date.valueOf() < now.valueOf() ? 'disabled' : '';
        }
    }).on('changeDate', function(ev) {
        if (ev.date.valueOf() > checkout.date.valueOf()) {
            var newDate = new Date(ev.date)
            newDate.setDate(newDate.getDate() + 1);
            checkout.setValue(newDate);
        }
        checkin.hide();
        $('.dpd2')[0].focus();
    }).data('datepicker');
    var checkout = $('.dpd2').datepicker({
        onRender: function(date) {
            return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
        }
    }).on('changeDate', function(ev) {
        checkout.hide();
    }).data('datepicker');
});

//date picker end

var myTable = $('#documentos-table').dataTable({
    "bDestroy": true,
    "bPaginate": false,
    "bFilter": false,
    "bInfo": false,
    "aoColumnDefs": [
        {"bSortable": false, "aTargets": [0]}
    ],
    "aaSorting": [[1, 'desc']],
    "aoColumns": [
        /*info*/null,
        /*usuario*/null,
        /*tipo*/null,
        /*correo*/null,
        /*activo*/null
    ]
});

var Script = function() {
//    consultarFacturasAutorizadas();
}();

function consultarFacturasAutorizadas() {
    var fechaInicio = document.getElementById("fechaInicio").value;
    var fechaFinal = document.getElementById("fechaFinal").value;

    url = "FacturaCtrl?op=buscarFacturasAutorizadas";
    url = url + "&fechaInicio=" + fechaInicio + "&fechaFinal=" + fechaFinal;
    ai = new AJAXInteraction(url, cargarFacturasAutorizadas, "XML");
    ai.doGet();

}

function cargarFacturasAutorizadas(result) {
    myTable.fnClearTable();

    var xmlresult = result.getElementsByTagName('factura');

    var html = "";
    for (i = 0; i < xmlresult.length; i++) {
        var factura = xmlresult[i];
        html += "<tr id='" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "-row'>";

        html += "<td class='hidden-phone'>" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "</td>";
        html += "<td class='hidden-phone'>" + factura.getElementsByTagName('numfactura')[0].firstChild.nodeValue + "</td>";
        html += "<td >" + factura.getElementsByTagName('fechaemision')[0].firstChild.nodeValue + "</td>";
        html += "<td>" + factura.getElementsByTagName('importetotal')[0].firstChild.nodeValue + "</td>";
        html += "<td>";
        html += "<input id='claveacceso-" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "' name='claveacceso-" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "' type='hidden' value='" + factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue + "'></input>";
        html += "<a href='FacturaCtrl?op=verFacturaAutorizada&claveacceso="+ factura.getElementsByTagName('claveacceso')[0].firstChild.nodeValue +"' target='_blank'>Ver</a>";
        html += "</td>";
        html += "</tr>";    
        document.getElementById("documentos-table").tBodies[0].innerHTML = html;
    }
    
}