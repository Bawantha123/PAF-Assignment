/**
 * 
 */
/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "orderAPI",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onItemSaveComplete(response.responseText, status);
			}
		});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).data("itemid"));
	$("#CusName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#CusEmail").val($(this).closest("tr").find('td:eq(1)').text());
	$("#CusAdress").val($(this).closest("tr").find('td:eq(2)').text());
	$("#CusPhone").val($(this).closest("tr").find('td:eq(3)').text());
	$("#itemID").val($(this).closest("tr").find('td:eq(4)').text());
});


$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "orderAPI",
			type: "DELETE",
			data: "Order_ID=" + $(this).data("itemid"),
			dataType: "text",
			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		}
		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}
	else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	}
	else {
			$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}

}




// CLIENT-MODEL================================================================
function validateItemForm() {
	// CODE
	if ($("#CusName").val().trim() == "") {
		return "Insert Customer Name.";
	}
	// NAME
	if ($("#CusEmail").val().trim() == "") {
		return "Insert Customer Email.";
	}
	// PRICE-------------------------------
	if ($("#CusAdress").val().trim() == "") {
		return "Insert Customer Address.";
	}
	// DESCRIPTION------------------------
	if ($("#CusPhone").val().trim() == "") {
		return "Insert Customer Contact Number.";
	}
	// DESCRIPTION------------------------
	if ($("#itemID").val().trim() == "") {
		return "Insert Item Code.";
	}
	return true;
}


