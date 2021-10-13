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
        var projectGrid = $('#projects')
        var itemGrid = $('#itemsToBeAdded')
        var filter = '';

        var chosenProject = {
            projectNr: '',
            description: ''
        }

        //URL and requestId for ERP
        // var url = 'http://erp-02/jetty/FormularzWprowadzaniaFaktur/api/'
        // var id = $('#RequestID').val();
        // var type = $('#FreeTextField_10).val();

        //URL and requestId for localhost
        var url = 'http://localhost:8080/api/'
        var id = '{10445D6C-7C86-44E7-9D15-DF2F0F23946D}';
        var type = 462;

        var dialogAdd  = $('#dialogAdd').dialog({
            autoOpen: false,
            width: 'auto'
        })

        var dialogProjects = $('#dialogProjects').dialog({
            autoOpen: false,
            width:'auto'
        })

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
                        refreshLineGrid();
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
                generateItemGrid();
                disableButton();
                dialogAdd.dialog('open');
            }.bind(this),
            position: "first",
            title: "Dodaje towar do zamówienia zakupu",
            cursor: "pointer"

        })

        $('#chooseProject').button().click(function () {
            filter = 'null';
            var getUrl = url + 'project=' + filter;
            $('#inputFilterProjects').val('')
            dialogProjects.dialog('open')

            projectGrid.jqGrid({
                url: getUrl,
                height: 'auto',
                maxHeight: 230,
                width: 'auto',
                maxWidth: 600,
                datatype: 'json',
                rownumbers: true,
                cmTemplate: {sortable: false, search: false},
                mtype: 'GET',
                search: true,
                caption: 'Projekty',
                colModel: [
                    {name: 'projectNr', label: 'Kod projektu', width: 120},
                    {name: 'description', label: 'Nazwa projektu', width: 250},
                ]
            })
        })

        $('#inputFilterProjects').keydown(function (e) {
            var keyCode = e.keyCode ? e.keyCode : e.which;
            if (keyCode == 13) {
                e.stopPropagation()
                $('#findProjects').trigger('click');
            }
        })

        $('#findProjects').button().click(function () {
            filter = $('#inputFilterProjects').val();
            var getUrl = url + 'project=' + filter;
            $('#alertProjekty').html('')
            projectGrid.jqGrid('setGridParam', {url: getUrl});
            projectGrid.trigger('reloadGrid');
        })

        $('#projectsAccept').button().click(function () {
            var rowid = projectGrid.jqGrid('getGridParam', 'selrow');
            if (rowid !== null) {
                $('#alertProjects').html('')
                chosenProject.projectNr = rowid;
                chosenProject.description = projectGrid.jqGrid('getCell', rowid, 'description');
                dialogProjects.dialog('close');
                generateLinkToProjectCard();
                refreshMPK(rowid, );
            } else {
                $('#alertProjects').html('Nie wybrano projektu')
            }
        })

        $('#closeDialogProjects').button().click(function (){
            dialogProjects.dialog('close')
        })

        $('#projectsClose').button().click(function (){
            dialogProjects.dialog('close')
        })

        $('#closeDialogAdd').button().click(function () {
            dialogAdd.dialog('close');
        })

        $('#addClose').button().click(function () {
            dialogAdd.dialog('close');
        })

        $('#mpkList').select2({
            sorter: data => data.sort((a, b) => a.text.localeCompare(b.text)),
        });

        $('#mpkList').change(function () {
            disableButton();
            refreshItemGrid();
        });

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
                    refreshLineGrid()
                },
            })
        }

        function refreshLineGrid() {
            lineGrid.jqGrid('setGridParam', {data: lines});
            lineGrid.trigger('reloadGrid');
        }

        function generateItemGrid(){
            var getUrl = 'mu/' + chosenProject.projectNr +'/' + $('#mpkList').val();
            odswiezGridZTowarami(url);
            gridTowaryDoDodania.jqGrid({
                url: getUrl,
                height: 'auto',
                maxHeight: 230,
                width: 'auto',
                maxWidth: 600,
                datatype: 'json',
                rownumbers: true,
                cmTemplate: {sortable: false, search: false},
                mtype: 'GET',
                search: true,
                caption: 'Towary',
                onCellSelect: function () {
                    disableButton();
                },
                colModel: [
                    {name: 'kod', label: 'Kod towaru', width: 150},
                    {name: 'towar', label: 'Opis', width: 400},
                ]
            })
        }

        var refreshItemsList = function () {
            getUrl = 'mu/' + chosenProject.projectNr +'/' + $('#mpkList').val();
            itemGrid.jqGrid('setGridParam', {url: getUrl});
            itemGrid.trigger('reloadGrid');
            // if (editedLine !== null) {
            //     setTimeout(function () {
            //         itemGrid.jqGrid('setSelection', editedLineId, true);
            //     }, 300)
            // }
            disableButton();
        }

        function generateLinkToProjectCard() {
            $('#project').html('<a href="http://erp-02/synergy/docs/ProCard.aspx?Project=' + chosenProject.projectNr +
                '" target="_blank" style="text-decoration: underline">'
                + chosenProject.projectNr + '</a><br>' + chosenProject.description);
        }
        function disableButton () {
            $('#addAccept').attr('disabled', true).css('opacity', '.35');
        }

        function refreshMPK(projectNr, mpk) {
            var getUrl = url + 'mpk/' + projectNr
            $.ajax({
                type: "GET",
                url: getUrl,
                dataType: "json"
            }).done(function (data, textStatus, xhr) {
                var budgetElements = data[0].budgetElements;
                var select = $('#mpkList');
                select.empty();
                $.each(budgetElements, function (key,value){
                    var attr = '';
                    var tempMpk = value.mpk.slice(0,-2)
                    if (mpk==tempMpk)
                        attr = 'selected'
                    select.append(`<option value="${tempMpk}" ${attr}>${tempMpk}</option>`)
                })


                // $('#wybierzMPK').html(data);
                // if (mpk !== null) {
                //     $('#wybierzMPK').val(mpk).trigger('change');
                // }
                // odswiezGridZTowarami()
            });
        }
    })
})


