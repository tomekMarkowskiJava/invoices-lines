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
    'http://erp-01/libs/vm/jqGrid.js',
    'http://erp-02/jetty/FormularzFaktur/formularz.css',
    '/formularz.css'
);


head.ready(function () {
    $(document).ready(function () {
        var lines = [];
        var taxes = [];
        var lineGrid = $('#lineGrid')
        var projectGrid = $('#projects')
        var itemGrid = $('#itemsToBeAdded')
        var filter = '';
        var nextId = 0;

        var chosenProject = {
            projectNr: '0',
            description: ''
        }

        // //URL and requestId for ERP
        // var url = 'http://erp-02/jetty/FormularzWprowadzaniaFaktur/api/'
        // var id = $('#RequestID').val();
        // var type = $('#FreeTextField_10').val();
        //
        // $('#FreeTextField_10').hide();

        //URL and requestId for localhost
        var url = 'http://localhost:8080/FormularzWprowadzaniaFaktur-0.0.1-SNAPSHOT/api/'
        var id = '{10445D6C-7C86-44E7-9D15-DF2F0F23946D}';
        var type = 462;

        var dialogAdd = $('#dialogAdd').dialog({
            autoOpen: false,
            width: 'auto'
        })

        var dialogProjects = $('#dialogProjects').dialog({
            autoOpen: false,
            width: 'auto'
        })

        var dialogLoading = $('#dialogLoading').dialog({
            autoOpen: false,
            width: 'auto'
        })

        saveInvoice(url, id, type);
        getTaxes();

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
            caption: 'Towary na fakturze',
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
                            id: rowid,
                            requestId: id
                        }
                        $.ajax({
                            url: url,
                            type: 'DELETE',
                            dataType: 'json',
                            data: params,
                            success: function (data) {
                                lineGrid.jqGrid("delRowData", rowid);
                                getRequestData();
                            }
                        })
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
                generateLinkToProjectCard();
                dialogLoading.dialog('open');
                refreshMPK(rowid, null);
            } else {
                $('#alertProjects').html('Nie wybrano projektu')
            }
        })

        $('#closeDialogProjects').button().click(function () {
            dialogProjects.dialog('close')
        })

        $('#projectsClose').button().click(function () {
            dialogProjects.dialog('close')
        })

        $('#closeDialogAdd').button().click(function () {
            dialogAdd.dialog('close');
        })

        $('#addAccept').button().click(function () {
            $('#alertAddIdem').html('');
            var rowid = itemGrid.jqGrid('getGridParam', 'selrow');
            if (rowid === null) {
                $('#alertAddIdem').html('Nie wybrano towaru');
            } else if ($('#itemPrice').val().length < 1 || $('#itemQuantity').val().length < 1) {
                $('#alertAddIdem').html('Nie wypełniono wszystkich pól');
            } else {
                dialogAdd.dialog('close');
                var price = parseFloat($('#itemPrice').val());
                var vat = $('#vatList').val();
                var description;
                if ($('#itemDescription').val()) {
                    description = $('#itemDescription').val();
                } else {
                    description = itemGrid.jqGrid('getCell', rowid, 'description')
                }
                let item = {
                    lineId: nextId,
                    itemCode: itemGrid.jqGrid('getCell', rowid, 'itemCode'),
                    description: description,
                    mpk: $('#mpkList').val(),
                    project: chosenProject.projectNr + ' ' + chosenProject.description,
                    quantity: $('#itemQuantity').val(),
                    netAmount: price.toFixed(2),
                    tax: $('#vatList option:selected').text(),
                    budzet: 0,
                    grossAmount: null,
                    value: null,
                    taxPercent: null,
                    projectName: chosenProject.description,
                    projectCode: chosenProject.projectNr,
                    itemId: rowid
                }
                var vat;
                $.each(taxes, function (key, value) {
                    if ($('#vatList option:selected').val() == value.id){
                        vat = value.taxPercent;
                        item.taxPercent = vat;
                        return false;
                    }
                })
                var grossAmount = parseFloat(Number(item.netAmount) + Number(item.netAmount * vat / 100));
                item.grossAmount = grossAmount.toFixed(2)
                item.value = (item.grossAmount * item.quantity).toFixed(2);
                // if (edycjaTowaru) {
                //     var edytowany = gridTowary.jqGrid('getGridParam', 'selrow');
                //     item.id = edytowany
                //     towary.forEach(function (item, index) {
                //         if (edytowany == item.id) {
                //             towary.splice(index, 1, item)
                //             var parametry = {
                //                 metoda: "edytujTowar",
                //                 towar: JSON.stringify(item),
                //                 idZamowienia: idZamowienia
                //             }
                //             $.post(poczatekUrl, parametry, function () {
                //             })
                //         }
                //     })
                // } else {
                //     var parametry = {
                //         metoda: "dodajTowar",
                //         idZamowienia: idZamowienia,
                //         towar: JSON.stringify(item)
                //     }
                //     $.post(poczatekUrl, parametry, function () {
                //         idKolejnegoTowaru++;
                //     })
                // }
                $.ajax({
                    url: url + 'addline/' + id,
                    type: 'POST',
                    data: JSON.stringify(item),
                    contentType: 'application/json; charset=utf-8',
                    success: function (data) {
                        getRequestData();
                        itemGrid.jqGrid('setSelection', item.id, true);
                    },
                })

            }
        })

        $('#addClose').button().click(function () {
            dialogAdd.dialog('close');
        })

        $('#mpkList').select2({
            closeOnselect: true,
            sorter: data => data.sort((a, b) => a.text.localeCompare(b.text))
        });

        $('#mpkList').change(function () {
            disableButton();
            refreshItemsGrid();
        });

        function saveInvoice(url, id, type) {
            invoice = {
                requestId: id,
                type: type
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: JSON.stringify(invoice),
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data == 0)
                        getRequestData();
                },
            })
        }

        function getTaxes() {
            $.ajax({
                url: url,
                type: 'GET',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    taxes = data;
                    var select = $('#vatList');
                    select.empty();
                    $.each(taxes, function (key, value) {
                        var tempId = value.id
                        var tempDesc = tempId + ' - ' + value.description;
                        select.append(`<option value="${tempId}">${tempDesc}</option>`)
                    })
                },
            })
        }

        function getRequestData() {
            $.ajax({
                url: url + 'invoice/' + id,
                type: 'GET',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    lines = data
                    refreshLineGrid()
                },
            })
        }

        function refreshLineGrid() {
            lineGrid.jqGrid('setGridParam', {data: lines});
            lineGrid.trigger('reloadGrid');
        }

        function generateItemGrid() {
            var getUrl = url + 'mu/0/0';
            // refreshItemsGrid(url);
            itemGrid.jqGrid({
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
                    {name: 'itemCode', label: 'Kod towaru', width: 150},
                    {name: 'description', label: 'Opis', width: 400},
                ]
            })
        }

        var refreshItemsGrid = function () {
            var getUrl = url + 'mu/' + chosenProject.projectNr + '/' + $('#mpkList').val();
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

        function disableButton() {
            // $('#addAccept').attr('disabled', true).css('opacity', '.35');
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
                $.each(budgetElements, function (key, value) {
                    var attr = '';
                    var tempMpk = value.mpk.slice(0, -2)
                    if (mpk == tempMpk)
                        attr = 'selected'
                    select.append(`<option value="${tempMpk}" ${attr}>${tempMpk}</option>`)
                })
                dialogLoading.dialog('close');
                dialogProjects.dialog('close');
                select.trigger('change');
            });
        }
    })
})


