function changeImg(id) {
  let imgPath = document.getElementById(id).getAttribute('src');
  document.getElementById('img-main').setAttribute('src', imgPath);
}
//update-product
var anchors = document.getElementsByName('update-taga'),
anchorsArray = Array.prototype.slice.call(anchors);
anchorsArray.forEach(function(elem, index) {
  elem.addEventListener('click', function() {
   // e.preventDefault();
    var text = document.getElementsByName("id-product")[index].innerHTML;
    var quantity=document.getElementsByName("quantity")[index].value;
    window.location.href = '/updateCart?idproduct='+text+'&&quantity='+quantity;
  });
});

function buyNow(){
    var idProduct = document.getElementById("id-product").innerHTML;
    var quantity=document.getElementById("quantity").value;
    window.location.href = '/buyNow?idProduct='+idProduct+'&&quantity='+quantity;
  }


function toggle(source) {
  checkboxes = document.getElementsByName('category');
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].checked = source.checked;
  }
}
function checkOut(){
  var idProduct = document.getElementById("id-product").innerHTML;
  var quantity=document.getElementById("quantity").value;
  var idTransport=document.getElementById("id-transport").value;
  window.location.href = '/buy-one-product?idTransport='+idTransport+'&&idProduct='+idProduct+'&&quantity='+quantity;
}
function goCheckOut(){
  var idTransport=document.getElementById("idTransport").value;
  window.location.href = '/checkout?idTransport='+idTransport;
}
function togglePrice(source) {
  checkboxes = document.getElementsByName('price');
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].checked = source.checked;
  }}


