function popupDeleteWindow(id, selectedRoomID) {
    document.getElementById('roomID').value = selectedRoomID;
    
    document.getElementById(id).style.height = "100%";
    document.getElementById("warningDeleteMessage").style.height = "100%";

}

function closeDeleteWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningDeleteMessage").style.height = "0%";
}

function popupConfirmWindow(id) {   
    document.getElementById(id).style.height = "100%";
    document.getElementById("warningMessage").style.height = "100%";

}

function closeConfirmWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningMessage").style.height = "0%";
}

function popupFeedbackWindow(id, bookingID, roomID) { 
    document.getElementById('selectBookingID').value = bookingID;
    document.getElementById('selectRoomID').value = roomID;
    
    document.getElementById(id).style.height = "100%";
    document.getElementById("warningFeedbackMessage").style.height = "100%";

}

function closeFeedbackWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningFeedbackMessage").style.height = "0%";
}
