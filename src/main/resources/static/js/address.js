var citis = document.getElementById("city");
var districts = document.getElementById("district");
var wards = document.getElementById("ward");
var address1="";
var address2="";
var address3="";
var Parameter = {
    url: "json/vn.json", //Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
    method: "GET", //do backend cung cấp
    responseType: "application/json", //kiểu Dữ liệu trả về do backend cung cấp
};
//gọi ajax = axios => nó trả về cho chúng ta là một promise
var promise = axios(Parameter);
//Xử lý khi request thành công
promise.then(function (result) {
    renderCity(result.data);
});

function renderCity(data) {
    for (const x of data) {
        citis.options[citis.options.length] = new Option(x.Name, x.Id);
    }

    // xứ lý khi thay đổi tỉnh thành thì sẽ hiển thị ra quận huyện thuộc tỉnh thành đó
    citis.onchange = function () {
        district.length = 1;
        ward.length = 1;
        if(this.value != ""){
            const result = data.filter(n => n.Id === this.value);

            for (const k of result[0].Districts) {
                district.options[district.options.length] = new Option(k.Name, k.Id);
            }
            address1=result[0].Name;


        }
    };

    // xứ lý khi thay đổi quận huyện thì sẽ hiển thị ra phường xã thuộc quận huyện đó
    district.onchange = function () {
        ward.length = 1;
        const dataCity = data.filter((n) => n.Id === citis.value);
        if (this.value != "") {
            const dataWards = dataCity[0].Districts.filter(n => n.Id === this.value)[0].Wards;

            for (const w of dataWards) {
                wards.options[wards.options.length] = new Option(w.Name, w.Id);
            }
            address2=dataCity[0].Districts.filter(n => n.Id === this.value)[0].Name;
        }
    };
    wards.onchange = function () {

        if (this.value != "") {
            const dataDistricts = data.filter((n) => n.Id === citis.value)[0].Districts.filter((n) => n.Id === districts.value)[0].Wards.filter(n=>n.Id===this.value);
            address3=dataDistricts[0].Name;

            document.getElementById("address1").setAttribute("value",address3+", "+address2+", "+address1);

        }




    };

}