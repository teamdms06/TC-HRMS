/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function toggleSalaryView(icon) {

    let salaryElement = $("#currentSalary");

    let actualSalary =
            salaryElement.attr("data-salary");

    if (salaryElement.text().includes("•")) {

        salaryElement.text(
                "₹ " + Number(actualSalary)
                .toLocaleString("en-IN")
                );

        $(icon).removeClass("mdi-eye-outline")
                .addClass("mdi-eye-off-outline");

    } else {

        salaryElement.text("₹ ••••••");

        $(icon).removeClass("mdi-eye-off-outline")
                .addClass("mdi-eye-outline");
    }
}
function toggleSalarySection(icon) {

    let salaryFields = [
        "#empNet",
        "#empCtc",
        "#empGross",
        "#empBasic",
        "#empHra",
        "#empPf",
        "#empPt",
        "#empConveyance",
        "#empSpecialAllowance",
        "#empInsurance"
    ];

    let isMasked =
            $("#empNet").text().includes("•");

    salaryFields.forEach(function (field) {

        let element = $(field);

        let salary =
                element.attr("data-salary");

        if (isMasked) {

            element.text(
                    "₹ " + Number(salary)
                    .toLocaleString("en-IN")
                    );

        } else {

            element.text("₹ ••••••");
        }
    });

    if (isMasked) {

        $(icon)
                .removeClass("mdi-eye-outline")
                .addClass("mdi-eye-off-outline");

    } else {

        $(icon)
                .removeClass("mdi-eye-off-outline")
                .addClass("mdi-eye-outline");
    }
}
$(document).ready(function () {

    $('#employeeSearch').select2({
        placeholder: "Search employee name or employee ID",
        minimumInputLength: 2,
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

    $('#employeeSearch').on('change', function () {

        let employeeId = $(this).val();

        if (!employeeId) {
            resetEmployeeProfile();
            return;
        }

        loadEmployeeProfile(employeeId);
        loadEmployeeDocumentCounts(employeeId);
//                                                            loadUploadedDocumentStatus(employeeId);
    });

});

function loadEmployeeProfile(employeeId) {

    $.ajax({
        url: "/employee-master-details",
        type: "GET",
        data: {
            employeeId: employeeId
        },

        success: function (res) {
//alert(res.aadhar)
            $("#empName").text(res.employeeName || '-');
            $("#empDesignation").text(res.designation || '-');
            $("#empCode").text(res.employeeId || '-');
            $("#empDepartment").text(res.department || '-');
            $("#empStatus").text(res.currentStatus || '-');
            $("#empGrade").text(res.grade || '-');

            $("#empInitials").text(getInitials(res.employeeName));

            $("#empEmail").text(res.emailId || '-');
            $("#empPhone").text(res.contactNumber || '-');
            $("#empAltPhone").text(res.alternateNumber || '-');
            $("#empGender").text(res.gender || '-');
            $("#empDoj").text(formatDate(res.dateOfJoining));
            $("#empDob").text(formatDate(res.dateOfBirth));
            $("#empLocation").text(res.location || '-');

            $("#empAadhar").text(res.aadhar || '-');
            $("#empPan").text(res.pan || '-');
            $("#empUan").text(res.uan || '-');
            $("#empPfStatus").text(res.pfStatus || '-');
            $("#empEducation").text(res.education || '-');
            $("#empState").text(res.state || '-');
            $("#empCity").text(res.city || '-');
            $("#empPincode").text(res.pincode || '-');
                                                                $("#empAddress").text(res.employeeAddress || '-');
          $("#empCompleteAddress").text(
    [
        res.employeeAddress,
        res.city,
        res.state,
        res.pincode
    ]
    .filter(v =>
        v &&
        v !== '-' &&
        v !== 'null' &&
        v.toString().trim() !== ''
    )
    .join(', ') || '-'
);
//                                                                $("#currentSalary").text(formatCurrency(res.finalSalary));
            $("#currentSalary").attr("data-salary", res.finalSalary);
            if (res.ctc) {
                $("#empNet").attr("data-salary", res.netSalary);
                $("#empCtc").attr("data-salary", res.ctc);
                $("#empGross").attr("data-salary", res.grossSalary);
                $("#empBasic").attr("data-salary", res.basic);
                $("#empHra").attr("data-salary", res.hra);
                $("#empPf").attr("data-salary", res.employeePf);
                $("#empPt").attr("data-salary", res.professionalTax);
                $("#empConveyance").attr("data-salary", res.conveyance);
                $("#empSpecialAllowance").attr("data-salary", res.specialAllowance);
                $("#empInsurance").attr("data-salary", res.insurance);
            } else {
                resetSalary();
            }
        },

        error: function (xhr) {
            Swal.fire("Error", xhr.responseText || "Failed to load employee details", "error");
        }
    });
}

function loadEmployeeDocumentCounts(employeeId) {

    $.ajax({
        url: "/employee-document-counts",
        type: "GET",
        data: {
            employeeId: employeeId
        },

        success: function (res) {

            $("#loiCount").text(res.loi || 0);
            $("#offerCount").text(res.offer || 0);
            $("#incrementCount").text(res.increment || 0);
            $("#promotionCount").text(res.promotion || 0);
            $("#expCount").text(res.exp || 0);
            $("#relievingCount").text(res.relieving || 0);
            $("#noiCount").text(res.noi || 0);

            let total =
                    (res.loi || 0)
                    + (res.offer || 0)
                    + (res.increment || 0)
                    + (res.promotion || 0)
                    + (res.exp || 0)
                    + (res.relieving || 0)
                    + (res.noi || 0);

            $("#totalDocs").text(total);
        },

        error: function () {
            Swal.fire("Error", "Failed to load document counts", "error");
        }
    });
}

function resetEmployeeProfile() {

    $("#empInitials").text("--");
    $("#empName").text("Select Employee");
    $("#empDesignation").text("Employee designation will appear here");
    $("#empCode").text("EMP ID");
    $("#empDepartment").text("Department");
    $("#empStatus").text("Status");

    $(".emp-info-list strong").text("-");
    $(".emp-doc-grid h5").text("0");
    $("#totalDocs").text("0");
    $("#currentSalary").text("₹0");

    resetSalary();
}

function resetSalary() {
    $("#empNet").text("₹0");
    $("#empCtc").text("-");
    $("#empGross").text("-");
    $("#empBasic").text("-");
    $("#empHra").text("-");
    $("#empPf").text("-");
    $("#empPt").text("-");
    $("#empConveyance").text("-");
    $("#empSpecialAllowance").text("-");
    $("#empInsurance").text("-");
}

function getInitials(name) {

    if (!name) {
        return "--";
    }

    let parts = name.trim().split(" ");

    if (parts.length === 1) {
        return parts[0].substring(0, 2).toUpperCase();
    }

    return (
            parts[0].charAt(0)
            + parts[1].charAt(0)
            ).toUpperCase();
}

function formatDate(dateString) {

    if (!dateString) {
        return "-";
    }

    let date = new Date(dateString);

    return date.toLocaleDateString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric'
    });
}

function formatCurrency(value) {

    if (value === null || value === undefined || value === "") {
        return "-";
    }

    return "₹" + Number(value).toLocaleString("en-IN");
}
function openEmployeeBulkUploadModal() {
    $("#bulkDocumentType").val("allMaster");
    $("#bulkUploadModal").modal("show");
}
$("#uploadForm").on("submit", function (e) {
    e.preventDefault();

    let formData = new FormData(this);
    let type = $("#bulkDocumentType").val();

    if (!type) {
        Swal.fire("Error", "Please select document type", "error");
        return;
    }

    if ($("#fileInput")[0].files.length === 0) {
        Swal.fire("Error", "Please select Excel file", "error");
        return;
    }

    $("#uploadBtn").prop("disabled", true);

    Swal.fire({
        title: "Upload Started",
        html: `
            <div style="text-align:left;">
                <b id="uploadPercentText">0%</b>
                <div class="progress mt-2" style="height:24px;">
                    <div id="uploadProgressBar"
                         class="progress-bar progress-bar-striped progress-bar-animated"
                         role="progressbar"
                         style="width:0%">
                        0%
                    </div>
                </div>
                <div id="uploadMessage" class="mt-2">
                    Preparing upload...
                </div>
            </div>
        `,
        allowOutsideClick: false,
        allowEscapeKey: false,
        showConfirmButton: false
    });

    $.ajax({
        url: "/upload-document-bulk",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,

        success: function (response) {

            // backend must return { jobId: "..." }
            let jobId = response.jobId;

            if (!jobId) {
                Swal.fire("Error", "Job ID not received from server", "error");
                $("#uploadBtn").prop("disabled", false);
                return;
            }

            pollUploadProgress(jobId, type);
        },

        error: function (xhr) {
            $("#uploadBtn").prop("disabled", false);

            Swal.fire({
                icon: "error",
                title: "Upload Failed",
                text: xhr.responseText || "Something went wrong"
            });
        }
    });
});

function pollUploadProgress(jobId, type) {

    let timer = setInterval(function () {

        $.ajax({
            url: "/upload-progress/" + jobId,
            type: "GET",

            success: function (res) {

                let percent = res.percent || 0;
                let message = res.message || "";

                $("#uploadPercentText").text(percent + "%");
                $("#uploadProgressBar")
                        .css("width", percent + "%")
                        .text(percent + "%");

                $("#uploadMessage").text(message);

                if (res.status === "COMPLETED") {
                    clearInterval(timer);

                    $("#uploadProgressBar")
                            .removeClass("progress-bar-animated")
                            .addClass("bg-success")
                            .css("width", "100%")
                            .text("100%");

                    Swal.fire({
                        icon: "success",
                        title: "Upload Successful",
                        text: message || "Upload completed successfully"
                    }).then(() => {

                        $("#bulkUploadModal").modal("hide");
                        $("#uploadForm")[0].reset();
                        $("#fileName").html("");
                        $("#uploadBtn").prop("disabled", false);

                        showTable(type);

                        setTimeout(function () {
                            showDataTable("pending");
                        }, 500);
                    });
                }

                if (res.status === "FAILED") {
                    clearInterval(timer);

                    $("#uploadBtn").prop("disabled", false);

                    Swal.fire({
                        icon: "error",
                        title: "Upload Failed",
                        text: message || "Something went wrong"
                    });
                }
            },

            error: function () {
                clearInterval(timer);
                $("#uploadBtn").prop("disabled", false);

                Swal.fire({
                    icon: "error",
                    title: "Progress Check Failed",
                    text: "Could not check upload progress"
                });
            }
        });

    }, 2000);
}

const dropZone = document.getElementById("dropZone");
const fileInput = document.getElementById("fileInput");

["dragenter", "dragover"].forEach(eventName => {

    dropZone.addEventListener(eventName, (e) => {

        e.preventDefault();
        dropZone.classList.add("drag-active");

    });

});

["dragleave", "drop"].forEach(eventName => {

    dropZone.addEventListener(eventName, (e) => {

        e.preventDefault();
        dropZone.classList.remove("drag-active");

    });

});

dropZone.addEventListener("drop", (e) => {

    const files = e.dataTransfer.files;

    if (files.length > 0) {

        fileInput.files = files;

        $("#fileName").html(`
                <div class="alert alert-success py-2 px-3 mb-0">
                    <strong>Selected File:</strong> ${files[0].name}
                </div>
            `);

    }

});
$("#fileInput").on("change", function () {

    let fileName = "";

    if (this.files.length > 0) {
        fileName = this.files[0].name;
    }

    $("#fileName").html(`
            <div class="alert alert-success py-2 px-3 mb-0">
                <strong>Selected File:</strong> ${fileName}
            </div>
        `);

});

let selectedEmployeeData = {};

function openBasicUpdateModal() {

    let employeeId = $("#employeeSearch").val();

    if (!employeeId) {
        Swal.fire("Error", "Please select employee first", "error");
        return;
    }
//    alert($("#empGrade").text())

    $("#editEmpId").val(employeeId);

    $("#editEmployeeName").val($("#empName").text());
    $("#editEmailId").val($("#empEmail").text());
    $("#editContactNumber").val($("#empPhone").text());
    $("#editAlternateNumber").val($("#empAltPhone").text());
    $("#editGender").val($("#empGender").text());
    $("#editLocation").val($("#empLocation").text());
    $("#editDepartment").val($("#empDepartment").text());
    $("#editDesignation").val($("#empDesignation").text());
    $("#editGrade").val($("#empGrade").text());
    $("#editEducation").val($("#empEducation").text());
    $("#editEmployeeAddress").val($("#empAddress").text());
    $("#editState").val($("#empState").text());
    $("#editCity").val($("#empCity").text());
    $("#editPincode").val($("#empPincode").text());
    $("#editAdhar").val($("#empAadhar").text());
    $("#editPan").val($("#empPan").text());
    $("#editUan").val($("#empUan").text());

    $("#basicUpdateModal").modal("show");
}

function updateBasicDetails() {

    let employeeId = $("#editEmpId").val();

    $.ajax({
        url: "/update-employee-basic-details",
        type: "POST",
        data: {
            employeeId: employeeId,
            employeeName: $("#editEmployeeName").val(),
            emailId: $("#editEmailId").val(),
            contactNumber: $("#editContactNumber").val(),
            alternateNumber: $("#editAlternateNumber").val(),
            gender: $("#editGender").val(),
            location: $("#editLocation").val(),
            department: $("#editDepartment").val(),
            designation: $("#editDesignation").val(),
            grade: $("#editGrade").val(),
            education: $("#editEducation").val(),
            employeeAddress: $("#editEmployeeAddress").val(),
            state: $("#editState").val(),
            city: $("#editCity").val(),
            pincode: $("#editPincode").val(),
            adhar: $("#editAdhar").val(),
            pan: $("#editPan").val()
        },

        success: function (response) {
            Swal.fire("Success", response, "success").then(() => {
                $("#basicUpdateModal").modal("hide");
                loadEmployeeProfile(employeeId);
            });
        },

        error: function (xhr) {
            Swal.fire(
                    "Error",
                    xhr.responseText || "Update failed",
                    "error"
                    );
        }
    });
}