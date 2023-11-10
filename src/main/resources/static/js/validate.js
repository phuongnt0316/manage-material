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