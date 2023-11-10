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
