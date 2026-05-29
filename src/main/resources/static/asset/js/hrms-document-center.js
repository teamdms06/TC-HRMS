/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let pendingTable;
$(document).ready(function () {


    pendingTable = $('#pendingTable').DataTable({pageLength: 5});
    $(".doc-check-item").each(function () {

        let label =
                $(this).find("span").text();

        $(this).attr("data-original", label);

    });
    $('#employeeSearch').select2({
        placeholder: "Search employee name or employee id",
        minimumInputLength: 1,
        width: '100%',
        ajax: {
            url: '/search-employees',
            dataType: 'json',
            delay: 300,
            data: function (params) {

                return {
                    keyword: params.term
                };
            },
            processResults: function (data) {

                return {
                    results: data
                };
            },
            cache: true
        }
    });


});
var selectedDocType = "";
var currentSelectedType = "";

function showTable(documentType) {

    $('#pendingTitle').text(documentType + " Document Data");

    let employeeId = $("#employeeSearch").val();

    if (!employeeId) {
        Swal.fire("Error", "Please select employee first", "error");
        return;
    }

    currentSelectedType = documentType;

    $("#documentSection").show();
    $("#pendingTableSection").show();
    $("#sentTableSection").hide();

    // instant scroll
    $('html, body').animate({
        scrollTop: $("#pendingTableSection").offset().top - 80
    }, 200);

    // show loading row
    pendingTable.clear();

    pendingTable.row.add([
        '',
        '',
        '',
        '',
        '<div class="text-primary">Loading...</div>'
    ]);

    pendingTable.draw(false);

    $.ajax({
        url: "/get-documentById",
        type: "GET",
        cache: false,
        data: {
            employeeId: employeeId,
            type: documentType,
            _t: new Date().getTime()
        },

        success: function (response) {

            let item = response.current;

            pendingTable.clear();

            let actionBtn = "";

            if (item.status === "GENERATED" || item.status === "SENT") {

                actionBtn = `
                    <button class="btn btn-sm btn-success"
                            onclick="openPdfModal('${item.pdfPath}', ${item.documentId})">
                        Preview
                    </button>

                    <button class="btn btn-sm btn-primary"
                            onclick="generateEmployeeDocument('${documentType}','${item.level}')">
                        Generate New
                    </button>
                `;

            } else {

                actionBtn = `
                    <button class="btn btn-sm btn-primary"
                            onclick="generateEmployeeDocument('${documentType}','${item.level}')">
                        Generate PDF
                    </button>
                `;
            }

            pendingTable.row.add([
                item.employeeName || '',
                item.employeeId || '',
                item.designation || '',
                formatDate(item.dateOfJoining),
                actionBtn
            ]);

            pendingTable.draw(false);

            loadDocumentHistory(response.history || []);
        },

        error: function (xhr) {

            Swal.fire(
                "Error",
                xhr.responseText || "Failed to load employee document data",
                "error"
            );
        }
    });
}function loadDocumentHistory(history) {

    if (!history || history.length === 0) {
        $("#documentHistorySection").hide();
        $("#documentHistoryBody").html("");
        return;
    }

    let rows = "";

    history.forEach(function (doc) {

        rows += `
            <tr>
                <td>${doc.documentNo || '-'}</td>
                <td>${doc.status || '-'}</td>
                <td>${doc.mailStatus || '-'}</td>
                <td>${formatDate(doc.generatedOn)}</td>
                <td>${formatDate(doc.sentOn)}</td>
                <td>
                    <button class="btn btn-sm btn-success"
                            onclick="openPdfModal('${doc.pdfPath}', ${doc.id})">
                        Preview
                    </button>
                   
                </td>
            </tr>
        `;
    });

    $("#documentHistoryBody").html(rows);
    $("#documentHistorySection").show();
}

function showDataTable(status) {

    if (status === "sent") {
        $("#pendingTableSection").show();

        $("#sentTableSection").hide();

    }

}
function formatDate(dateString) {

    if (!dateString) {
        return '';
    }

    const date = new Date(dateString);

    return date.toLocaleDateString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric'
    });

}
function generatePdf(btnId) {

    var parts = btnId.split("-");

    var type = parts[0];
    var id = parts[1];

    Swal.fire({
        title: 'Generating PDF...',
        html: 'Please wait while document is being generated',
        allowOutsideClick: false,
        allowEscapeKey: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    $.ajax({
        url: "/generatePdf",
        type: "GET",
        data: {
            id: id,
            type: type
        },

        success: function (response) {

            Swal.fire({
                icon: 'success',
                title: 'PDF Generated Successfully',
                text: response,
                confirmButtonColor: '#3085d6'
            }).then(() => {

                if (typeof showTable === "function") {

                    showTable(currentSelectedType);

                    setTimeout(() => {
                        showDataTable('pending');
                    }, 300);

                } else {

                    location.reload();

                }

            });

        },

        error: function (xhr) {

            Swal.close();

            let errorMsg = "Something went wrong!";

            if (xhr.responseText) {
                errorMsg = xhr.responseText;
            }

            Swal.fire({
                icon: 'error',
                title: 'PDF Generation Failed',
                text: errorMsg,
                confirmButtonColor: '#d33'
            });
        }
    });
}

var selectedPdfPath = "";
var selectedDocumentId = "";



function openPdfModal(s3Key, documentId) {
    selectedPdfPath = s3Key;
    selectedDocumentId = documentId;
    $.ajax({

        url: "/generate-preview-url",
        type: "GET",

        data: {
            path: s3Key
        },

        success: function (previewUrl) {

            $("#pdfFrame").attr(
                    "src",
                    previewUrl + "#toolbar=0&navpanes=0&scrollbar=0"
                    );

            $("#sendMailBtn").attr(
                    "onclick",
                    "sendEmployeeDocument(" + documentId + ")"
                    );

            $("#pdfPreviewModal").modal("show");
        },

        error: function () {

            Swal.fire(
                    "Error",
                    "Failed to load PDF preview",
                    "error"
                    );
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {

    const dropZone = document.getElementById("dropZone");
    const fileInput = document.getElementById("fileInput");
    const fileName = document.getElementById("fileName");

    fileInput.addEventListener("change", function () {
        if (fileInput.files.length > 0) {
            fileName.innerHTML = "Selected file: " + fileInput.files[0].name;
        }
    });

    dropZone.addEventListener("dragover", function (e) {
        e.preventDefault();
        dropZone.classList.add("drag-active");
    });

    dropZone.addEventListener("dragleave", function () {
        dropZone.classList.remove("drag-active");
    });

    dropZone.addEventListener("drop", function (e) {
        e.preventDefault();
        dropZone.classList.remove("drag-active");

        if (e.dataTransfer.files.length > 0) {
            fileInput.files = e.dataTransfer.files;
            fileName.innerHTML = "Selected file: " + e.dataTransfer.files[0].name;
        }
    });

});
$('#employeeSearch').on('change', function () {

    let employeeId = $(this).val();

    if (!employeeId) {
        resetDocumentCounts();
        return;
    }

    loadEmployeeDocumentCounts(employeeId);
    loadUploadedDocumentStatus(employeeId);
      $("#pendingTableSection").hide();
//        $("#pendingTable").html("");
      $("#documentHistorySection").hide();
        $("#documentHistoryBody").html("");
});
function loadEmployeeDocumentCounts(employeeId) {

    $.ajax({
        url: "/employee-document-counts",
        type: "GET",
        data: {
            employeeId: employeeId
        },

        success: function (res) {

            $("#appointmentCount").text(res.loi || 0);
            $("#agreementCount").text(res.offer || 0);
            $("#incrementCount").text(res.increment || 0);
            $("#promotionCount").text(res.promotion || 0);
            $("#expCount").text(res.exp || 0);
            $("#relievingCount").text(res.relieving || 0);
            $("#ndaCount").text(res.noi || 0);
            $("#warnCount").text(res.warning || 0);
        },

        error: function () {
            Swal.fire("Error", "Failed to load document count", "error");
        }
    });
}
function resetDocumentCounts() {
    $("#appointmentCount").text(0);
    $("#agreementCount").text(0);
    $("#incrementCount").text(0);
    $("#promotionCount").text(0);
    $("#expCount").text(0);
    $("#relievingCount").text(0);
    $("#ndaCount").text(0);
    $("#warnCount").text(0);
}

function generateEmployeeDocument(documentType, level) {
//    alert(level)
    let loginRole = $("#sessionRole").val();
    let employeeId = $("#employeeSearch").val();

    if (!employeeId) {
        Swal.fire("Error", "Please select employee first", "error");
        return;
    }
    if (documentType === "increment" || documentType === "promotion") {
        if (!checkDocumentPermission(level, loginRole)) {
            return;
        }
    }
    if (documentType === "warning") {
        $("#warningLetterModal").modal("show");
    } else {
      Swal.fire({
    title: "Generate PDF?",
    text: "Do you want to generate this document PDF?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Yes, Generate",
    cancelButtonText: "Cancel",
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33"
}).then((result) => {

    if (result.isConfirmed) {

        // loading popup
        Swal.fire({
            title: "Generating PDF...",
            text: "Please wait while PDF is generating",
            allowOutsideClick: false,
            allowEscapeKey: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
 $.ajax({
            url: "/generate-employee-document",
            type: "GET",
            data: {
                employeeId: employeeId,
                documentType: documentType
            },

            success: function (response) {
                Swal.fire({
                    icon: "success",
                    title: "PDF Generated",
                    text: response
                }).then(() => {
                    showTable(documentType);
                    loadEmployeeDocumentCounts(employeeId);
                });
            },

            error: function (xhr) {
                Swal.fire({
                    icon: "error",
                    title: "PDF Generation Failed",
                    text: xhr.responseText || "Something went wrong"
                });
            }
        });

    }

});

       
    }
}
function checkDocumentPermission(employeeLevel, loginRole) {
//    alert(loginRole)
//    alert(employeeLevel)
    let levelNumber =
            parseInt(employeeLevel.replace("L", ""));

    /* HR restriction for higher management */

    if (loginRole === "HR" && levelNumber < 5) {

        Swal.fire({
            icon: "warning",
            title: "Permission Denied",
            text: "HR is not authorized for higher management employees."
        });

        return false;
    }

    /* Allowed for President / VC / Director */

    if (
            loginRole === "President"
            || loginRole === "Vice President"
            || loginRole === "Director"
            || loginRole === "Founder"
            ) {

        return true;
    }

    return true;
}
function sendEmployeeDocument(documentId) {

    if (!documentId) {
        Swal.fire("Error", "Document id not found", "error");
        return;
    }

    Swal.fire({
        title: "Sending Mail...",
        text: "Please wait while document is sending",
        allowOutsideClick: false,
        allowEscapeKey: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    $.ajax({
        url: "/send-employee-document",
        type: "GET",
        data: {
            documentId: documentId
        },

        success: function (response) {

            Swal.fire({
                icon: "success",
                title: "Mail Sent",
                text: response
            }).then(() => {

                $("#pdfPreviewModal").modal("hide");

                if (currentSelectedType) {
                    showTable(currentSelectedType);
                }

                let employeeId = $("#employeeSearch").val();

                if (employeeId) {
                    loadEmployeeDocumentCounts(employeeId);
                }
            });
        },

        error: function (xhr) {
            Swal.fire({
                icon: "error",
                title: "Mail Sending Failed",
                text: xhr.responseText || "Something went wrong"
            });
        }
    });
}

function sendDocumentUploadLink() {

    let employeeId = $("#employeeSearch").val();

    if (!employeeId) {
        Swal.fire("Error", "Please select employee first", "error");
        return;
    }

    Swal.fire({
        title: "Send Upload Link?",
        text: "Employee will receive document upload link on email.",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "Send Link",
        cancelButtonText: "Cancel"
    }).then((result) => {

        if (result.isConfirmed) {

            Swal.fire({
                title: "Sending...",
                text: "Please wait",
                allowOutsideClick: false,
                didOpen: () => Swal.showLoading()
            });

            $.ajax({
                url: "/send-document-upload-link",
                type: "POST",
                data: {
                    employeeId: employeeId
                },

                success: function (response) {
                    Swal.fire("Success", response, "success");
                },

                error: function (xhr) {
                    Swal.fire(
                            "Failed",
                            xhr.responseText || "Unable to send upload link",
                            "error"
                            );
                }
            });
        }
    });
}
let uploadedDocumentsMap = {};
function loadUploadedDocumentStatus(employeeId) {

    resetUploadDocumentStatus();

    $.ajax({
        url: "/employee-uploaded-documents-by-employee",
        type: "GET",
        data: {
            employeeId: employeeId
        },

        success: function (response) {

            let uploaded = 0;
            let total = 10;

            response.forEach(function (doc) {

                let type = doc.documentType;
                let status = doc.verificationStatus || "PENDING";

                uploadedDocumentsMap[type] = doc;

                let card = $("#doc" + type);

                card.removeClass("uploaded verified rejected")
                        .css("cursor", "pointer")
                        .attr("onclick", "openUploadedDocumentModal('" + type + "')");

                if (status === "VERIFIED") {
                    card.addClass("verified");

                    card.find("span").html(`
                    <div class="doc-verified-content">
                        <span>${type.replaceAll("_", " ")}</span>

                        <div class="verified-badge">
                            <i class="mdi mdi-check-circle"></i>
                            Verified
                        </div>
                    </div>
                `);
                } else if (status === "REJECTED") {
                    card.addClass("rejected");

                    card.find("span").html(`
                            <div class="doc-verified-content">
                                <span>${type.replaceAll("_", " ")}</span>

                                <div class="rejected-badge">
                                    <i class="mdi mdi-close-circle"></i>
                                    Rejected
                                </div>
                            </div>
                        `);
                    uploaded--;
                } else {
                    card.addClass("uploaded");

                    card.find("span").html(`
                                                                    <div class="doc-verified-content">
                                                                    <span>${type.replaceAll("_", " ")}</span>

                                                                    <div class="pending-badge">
                                                                        <i class="mdi mdi-clock-outline"></i>
                                                                        Pending
                                                                    </div>
                                                                    </div>
                                                                          `);
                }

                uploaded++;
            });

            let percent = Math.round((uploaded / total) * 100);

            $("#uploadedDocCount").text(uploaded + " / " + total + " Uploaded");

            $("#uploadPercentText").text(percent + "%");

            let circumference = 176;
            let offset = circumference - (percent / 100) * circumference;

            $("#progressCircle").css(
                    "stroke-dashoffset",
                    offset
                    );

            $("#uploadProgressBar").css("width", percent + "%");
        },

        error: function () {
            console.log("Failed to load uploaded documents");
        }
    });
}
//function resetUploadDocumentStatus() {
//
//    $(".doc-check-item").removeClass("uploaded");
//
//    $("#uploadedDocCount").text("0 / 10 Uploaded");
//
//    $("#uploadProgressBar").css("width", "0%");
//}
function openUploadedDocumentModal(documentType) {

    let doc = uploadedDocumentsMap[documentType];

    if (!doc) {
        Swal.fire("Error", "Document not found", "error");
        return;
    }

    $("#uploadedDocTitle").text(documentType + " Document");

    $.ajax({
        url: "/generate-employee-upload-preview-url",
        type: "GET",
        data: {
            documentId: doc.id
        },

        success: function (previewUrl) {
            console.log(previewUrl);
            $("#uploadedDocFrame").attr("src", previewUrl);

            $("#verifyDocumentBtn").attr(
                    "onclick",
                    "verifyUploadedDocument(" + doc.id + ", 'VERIFIED')"
                    );

            $("#rejectDocumentBtn").attr(
                    "onclick",
                    "verifyUploadedDocument(" + doc.id + ", 'REJECTED')"
                    );

            $("#uploadedDocModal").modal("show");
        },

        error: function (xhr) {
            Swal.fire("Error", xhr.responseText || "Preview failed", "error");
        }
    });
}
function verifyUploadedDocument(documentId, status) {

    $.ajax({
        url: "/verify-uploaded-document",
        type: "POST",
        data: {
            documentId: documentId,
            status: status
        },

        success: function (response) {

            Swal.fire("Success", response, "success");

            $("#uploadedDocModal").modal("hide");

            let employeeId = $("#employeeSearch").val();
            loadUploadedDocumentStatus(employeeId);
        },

        error: function (xhr) {
            Swal.fire("Error", xhr.responseText || "Update failed", "error");
        }
    });
}

$('#warningType').on('change', function () {

    let type = $(this).val();

    let incidentOptions = [];

    if (type === 'quality') {

        incidentOptions = [
            'Quality deviation',
            'Audit non-compliance',
            'Incorrect evaluation',
            'Delay in feedback sharing'
        ];

    } else if (type === 'hr') {

        incidentOptions = [
            'Attendance issue',
            'Behavioral misconduct',
            'Unauthorized absence',
            'Policy violation'
        ];

    } else if (type === 'operation') {

        incidentOptions = [
            'Process negligence',
            'Poor productivity',
            'Mismanagement',
            'Non-adherence to SOP'
        ];

    } else if (type === 'admin') {

        incidentOptions = [
            'Administrative negligence',
            'Delay in support',
            'Asset mismanagement',
            'Discipline issue'
        ];
    }

    let html = '<option value="">Select Incident</option>';

    incidentOptions.forEach(function (item) {

        html += `
            <option value="${item}">
                ${item}
            </option>
        `;
    });

    $('#incidentReported').html(html);
});
function saveWarningLetterForm() {

    let employeeId = $("#employeeSearch").val();

    let formData = {
        employeeId: employeeId,
        warningType: $("#warningType").val(),
        warningLevel: $("#warningLevel").val(),
        incidentReported: $("#incidentReported").val(),
        incidentDateTime: $("#incidentDateTime").val(),
        incidentReportedBy: $("#incidentReportedBy").val(),
        incidentDescription: $("#incidentDescription").val()
    };

    if (!formData.warningType ||
            !formData.warningLevel ||
            !formData.incidentReported ||
            !formData.incidentDateTime ||
            !formData.incidentReportedBy) {

        Swal.fire("Required", "Please fill all required fields", "warning");
        return;
    }

    Swal.fire({
        title: "Generating Warning Letter...",
        text: "Please wait",
        allowOutsideClick: false,
        didOpen: () => Swal.showLoading()
    });

    $.ajax({
        url: "/generate-warning-letter",
        type: "POST",
        data: formData,

        success: function (response) {
            Swal.fire("Success", response, "success").then(() => {
                $("#warningLetterModal").modal("hide");
                showTable("warning");
                loadEmployeeDocumentCounts(employeeId);
            });
        },

        error: function (xhr) {
            Swal.fire(
                    "Failed",
                    xhr.responseText || "Unable to generate warning letter",
                    "error"
                    );
        }
    });
}

function resetUploadDocumentStatus() {

    uploadedDocumentsMap = {};

    /* reset all cards */
    $(".doc-check-item").each(function () {

        let card = $(this);

        /* remove all classes */
        card.removeAttr("class");

        /* add default class */
        card.addClass("doc-check-item");

        /* remove click */
        card.removeAttr("onclick");

        /* reset cursor */
        card.css("cursor", "default");

        /* reset text */
        let originalText = card.attr("data-original");

        if (originalText) {

            card.html(`
                <i class="mdi mdi-file-document-outline"></i>
                <span>${originalText}</span>
            `);
        }

    });

    /* reset uploaded count */
    $("#uploadedDocCount").text("0 / 10 Uploaded");

    /* reset progress text */
    $("#uploadPercentText").text("0%");

    /* reset progress bar */
    $("#uploadProgressBar").css("width", "0%");

    /* reset circular progress */
    let circumference = 176;

    $("#progressCircle").css(
            "stroke-dashoffset",
            circumference
            );
}