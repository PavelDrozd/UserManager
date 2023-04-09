$(function() {
	const $createBtn = $("button.create");
	$createBtn.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		let formData = new FormData();
		const username = $("#input-username").val();
		const email = $("#input-email").val();
		const user = {username, email};
		formData.append("user", JSON.stringify(user));
		formData.append("file1", $("#input-file1")[0].files[0]);
		formData.append("file2", $("#input-file2")[0].files[0]);
		formData.append("file3", $("#input-file3")[0].files[0]);
		$.ajax({
			url: "/api/users/register",
			method: "POST",
			data: formData,
			processData: false,
            contentType: false,
			success: processCreated,
			error: processError,
		});
	}

	function processCreated(data, status, $XHR) {
		$(".error").remove();
		if ($XHR.status === 201) {
			const uri = $XHR.getResponseHeader("Location");
			window.location.href = uri;
		} else {
			alert("Couldn't create user. Server error");
		}
	}

    function processError(response) {
		$(".error").remove();
		if (response.status === 422) {
			const validationError = response.responseJSON;
			for (const field in validationError.messages) {
				validationError.messages[field].forEach(msg => {
					$("form").prepend($(`<div class="error">${field}: ${msg}</div>`));
				})
			}
		}
	}

});
