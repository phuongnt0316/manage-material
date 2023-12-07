function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
function validate() {
    var x= document.getElementById("password").value;
    var y= document.getElementById("rePassword").value;
    if(x==y) return true;
    else alert("Mật khẩu không trùng nhau");
}
function changeDate(){
    var month=document.getElementById("date-sale").value;
    window.location.href = '/revenue?month='+month;
}
// function validateEmail() {
//     var x = document.getElementById("email").value;
//     var atposition = x.indexOf("@");
//     var dotposition = x.lastIndexOf(".");
//     if (atposition < 1 || dotposition < (atposition + 2)
//         || (dotposition + 2) >= x.length) {
//         alert("Sai định dạng email, vui lòng kiểm tra lại");
//     }
//     else  return true;
// }
function validateEmail(){
    var email=document.getElementById("email").value;
    if (email.match(
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    ))
        return true;
    else alert("Sai định dạng email!");
}



function onchangeIdproduct(id,id1){
  //  console.log(product1);
    window.location.href = '/add-quantity-product?'+id1+'='+id;
}