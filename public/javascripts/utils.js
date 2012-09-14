function setCorrectPosition(elementID) {
	var width = window.innerWidth;
	var height = window.innerHeight;	
	document.getElementById(elementID).style.position = "fixed";
	document.getElementById(elementID).style.left = width - 50 + "px";
	document.getElementById(elementID).style.top = 10 + "px"
}

function centralizeElement(elementID) {
    var browserWidth = window.innerWidth;
    var browserHeight = window.innerHeight;
    var element = document.getElementById(elementID);
    element.style.position = "fixed";
    element.style.left = (browserWidth - element.offsetWidth)/2 + "px";
    element.style.top = (browserHeight - element.offsetHeight * 2)/2 + "px";
  
}