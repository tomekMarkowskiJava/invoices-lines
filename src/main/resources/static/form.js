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

head.ready(function (){
    $(document).ready(function (){
        var lineGrid = $('#lineGrid')
        var lines =[];

        var testObject =
            {
                id:1,
                itemCode: '12345',
                itemDescription: 'testowy towar',
                project: '20-2000.20-20',
                mpk: 'SIT',
                quantity: 3,
                netto: 10,
                brutto: 12.30,
                value: 36.90,
                vat: 6.90,
                budget: true
            }

        lines.push(testObject)

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
                {name: "itemDescription", label: "Opis", width: 60},
                {name: "project", label: "Projekt", width: 90},
                {name: "mpk", label: "MPK", width: 13},
                {name: "queantity", label: "Ilość", width: 15},
                {name: "netto", label: "Cena netto", width: 23},
                {name: "brutto", label: "Cena brutto", width: 23},
                {name: "value", label: "Wartość", width: 25},
                {name: "vat", label: "VAT", width: 50},
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
                    console.log('usun')
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
    })
})