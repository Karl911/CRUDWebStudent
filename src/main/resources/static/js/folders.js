var checks = document.querySelectorAll("input[type=checkbox]");

for(var i = 0; i < checks.length; i++){
  checks[i].addEventListener( 'change', function() {
    if(this.checked) {
       showChildrenChecks(this);
    } else {
       hideChildrenChecks(this)
    }
  });
}

function showChildrenChecks(elm) {
   var pN = elm.parentNode;
   var childCheks = pN.children;

  for(var i = 0; i < childCheks.length; i++){
      if(hasClass(childCheks[i], 'child-check')){
          childCheks[i].classList.add("active");      
      }
  }

}

function hideChildrenChecks(elm) {
   var pN = elm.parentNode;
   var childCheks = pN.children;

  for(var i = 0; i < childCheks.length; i++){
      if(hasClass(childCheks[i], 'child-check')){
          childCheks[i].classList.remove("active");      
      }
  }

}

function hasClass(elem, className) {
    return new RegExp(' ' + className + ' ').test(' ' + elem.className + ' ');
}