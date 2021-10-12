head.js(
    'http://erp-01/libs/font-awesome/css/font-awesome.min.css',
    'http://erp-01/libs/jquery-ui-1.12.1.custom/jquery-ui.min.css',
    'http://erp-01/libs/free-jqGrid-4.15.5/css/ui.jqgrid.css',
    'http://erp-01/libs/select2-4.0.13/css/select2.min.css',
    'http://erp-01/libs/jquery-3.2.1.min.js',
    'http://erp-01/libs/jquery-ui-1.12.1.custom/jquery-ui.min.js',
    'http://erp-01/libs/jquery-ui-1.12.1.custom/i18n/datepicker-pl.js',
    'http://erp-01/libs/select2-4.0.13/js/select2.min.js',
    'http://erp-01/libs/free-jqGrid-4.15.5/js/i18n/grid.locale-pl.js',
    'http://erp-01/libs/free-jqGrid-4.15.5/js/jquery.jqGrid.min.js',
    'http://erp-01/libs/jqGrid/plugins/grid.totals.js',
    'http://erp-01/libs/vm/jqGrid.js'
);


head.ready(function () {
    $(document).ready(function () {
        var lines = [];
        var lineGrid = $('#lineGrid')

        //URL and requestId for ERP
        // var url = 'http://erp-02/jetty/FormularzWprowadzaniaFaktur/api/'
        // var id = $('#RequestID').val();
        // var type = $('#FreeTextField_10).val();

        //URL and requestId for localhost
        var url = 'http://localhost:8080/api/'
        var id = '{10445D6C-7C86-44E7-9D15-DF2F0F23946D}';
        var type = 462;

        saveInvoice(url,id,type);

        // var testObject =
        //     {
        //         id: 1,
        //         itemCode: '12345',
        //         itemDescription: 'testowy towar',
        //         project: '20-2000.20-20',
        //         mpk: 'SIT',
        //         quantity: 3,
        //         netto: 10,
        //         brutto: 12.30,
        //         value: 36.90,
        //         vat: 6.90,
        //         budget: true
        //     }

        // lines.push(testObject)

        lineGrid.jqGrid({
            height: 'auto',
            width: $(window).outerWidth() - 40,
            iconSet: 'fontAwesome',
            rownumbers: true,
            pager: true,
            footerrow: true,
            caption: 'Towary',
            multiselect: false,
            colModel: [
                {name: "itemCode", label: "Towar", width: 45},
                {name: "description", label: "Opis", width: 60},
                {name: "project", label: "Projekt", width: 90},
                {name: "mpk", label: "MPK", width: 13},
                {name: "quantity", label: "Ilość", width: 15},
                {name: "netAmount", label: "Cena netto", width: 23},
                {name: "grossAmount", label: "Cena brutto", width: 23},
                {name: "value", label: "Wartość", width: 25},
                {name: "tax", label: "VAT", width: 50},
                {
                    name: "budget",
                    label: "Czy jest budżet?",
                    width: 20,
                    align: "center",
                    formatter: "checkbox",
                    editable: true
                }
            ],
            data: lines
        })

        lineGrid.jqGrid('showTotals', {total: 'SUMA', totalRow: 'brutto', includeRows: ['wartosc']});

        lineGrid.jqGrid('navGrid', {search: false, add: false, edit: false, del: false, refresh: false})
            .jqGrid('navButtonAdd', {
                caption: "Usuń",
                buttonicon: "fa-minus",
                onClickButton: function () {
                    var rowid = lineGrid.jqGrid('getGridParam', 'selrow')
                    if (rowid != null) {
                        var params = {
                            lineId: rowid,
                            requestId: id
                        }
                        $.ajax({
                            url: url,
                            type: 'DELETE',
                            dataType: 'json',
                            data: params
                        })
                        refreshGrid();
                    } else {

                    }
                }.bind(this),
                position: "first",
                title: "Usuwa towar z listy",
                cursor: "pointer"
            }).jqGrid('navButtonAdd', {
            caption: "Edytuj",
            buttonicon: "fa-edit",
            onClickButton: function () {
                console.log('edytuj');
            }.bind(this),
            position: "first",
            title: "Edytuje zaznaczony towar",
            cursor: "pointer"
        }).jqGrid('navButtonAdd', {
            caption: "Dodaj towar",
            buttonicon: "fa-plus",
            onClickButton: function () {
                console.log("dodaj")
            }.bind(this),
            position: "first",
            title: "Dodaje towar do zamówienia zakupu",
            cursor: "pointer"

        })



        function saveInvoice(url,id, type) {
            invoice = {
                requestId: id,
                type: type
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: JSON.stringify(invoice),
                contentType: 'application/json; charset=utf-8',
                success: function (data){
                    if (data == 0)
                        getRequestData(url,id);
                },
            })
        }

        function getRequestData(url, id) {
            $.ajax({
                url: url + 'invoice/' + id,
                type: 'GET',
                contentType: 'application/json; charset=utf-8',
                success: function (data){
                    lines = data
                    refreshGrid()
                },
            })
        }

        function refreshGrid() {
            lineGrid.jqGrid('setGridParam', {data: lines});
            lineGrid.trigger('reloadGrid');
        }
    })
})


