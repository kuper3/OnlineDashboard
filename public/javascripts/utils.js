function initForm () {
    addItem("addUserLink")
    addItem("loginLink")
    
    centralizeElement("content");
}

function click(to) {
    document.mainForm.submit()
}

function centralizeElement(elementID) {
    var browserWidth = window.innerWidth;
    var browserHeight = window.innerHeight;
    var element = document.getElementById(elementID);
    element.style.position = "fixed";
    element.style.left = (browserWidth - element.offsetWidth)/2 + "px";
    element.style.top = (browserHeight - element.offsetHeight)/2 + "px";
  
}

function addItem(itemId) {
    var width = window.innerWidth;
    var height = window.innerHeight;
    var shift = 0;
    if (itemId == "loginLink") {   
        shift = 120;
    }
    if (itemId == "addUserLink") {
        shift = 230;
    }
    
    document.getElementById(itemId).style.position = "fixed";
    document.getElementById(itemId).style.left = (width - shift) + "px";
    document.getElementById(itemId).style.top = 10 + "px";
}
