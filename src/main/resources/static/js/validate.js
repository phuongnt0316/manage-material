function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
function validate() {
    var x= document.getElementById("password").value;
    var y= document.getElementById("repassword").value;
    if(x==y) return true;
    else alert("Mật khẩu không trùng nhau");
}
function changeDate(){
    var month=document.getElementById("date-sale").value;
    window.location.href = '/admin?month='+month;
}
function onchangeIdproduct(id,id1){
  //  console.log(product1);
    window.location.href = '/add-quantity-product?'+id1+'='+id;
}