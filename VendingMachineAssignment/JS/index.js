var total=0;

var ds = new DataService();




function logError(err) {
  $('#messages').val(err.responseJSON.message);
  calculateChange(total);
}

function calculateChange(total){
   let quarters= Math.floor(total/25);
  total= total-(quarters*25);
  let dimes=Math.floor(total/10);
  total=total-(dimes*10);
  let nickels=Math.floor(total/5);
  total=total-(nickels*5);
}







//When user clicks on a specific box
function onVendingCardClick(e) {
  //alert("This has been clicked!");

  e.preventDefault();

  var btn = $(this);
  itemId = btn.data("vendid");
  console.log(itemId);
  $("#select-item").val(itemId);
  $('#messages').val('You have chosen Item # ' + itemId);


}



//load items on page
function loadItems(response) {
  //Do something
  $("#cards").empty();
  for (var i = 0; i < response.length; i++) {
    console.log(response[i]);
    var cardFormat = `<div class="vendingMachine-card col-md-4" align=center data-vendid="${response[i].id}">${response[i].name}<br>$${response[i].price.toFixed(2)}<br>Quantity Left:${response[i].quantity}</div`
    $("#cards").append(cardFormat);
  }
}



//make purchase button
function makePurchase(e) {
  e.preventDefault();
  itemId = $('#select-item').val();
  console.log("You have selected item number" + itemId);
  ds.money = $('#total-money').val();
  console.log("You have inserted  $" + ds.money);


  if (itemId == "" || ds.money == "") {
    alert("Please make a selection and insert money");
    return false;
  }

  ds.vendItem(ds.money, itemId, function(response) {
    $('#total-change').val("Quarters: " + response.quarters + "Dimes: " + response.dimes + "Nickels: " + response.nickels);
  }, logError);


  $('#messages').val('Thank you!!!');


}


//add money functions


function addDollar() {
  $('#total-change').empty();
  $('#total-change').append('');
  var money = parseFloat($('#total-money').val());

  if (isNaN(money)) {
    $('#total-money').val(1);
  } else {
    var totalMoney = parseFloat(money) + 1.00;
    $('#total-money').val(totalMoney.toFixed(2));
  }
}

function addQuarter() {

  $('#total-change').empty();
  $('#total-change').append('');
  var money = parseFloat($('#total-money').val());

  if (isNaN(money)) {
    $('#total-money').val(0.25);
  } else {
    var totalMoney = parseFloat(money) + 0.25;
    $('#total-money').val(totalMoney.toFixed(2));
  }
}

function addDime() {

  $('#total-change').empty();
  $('#total-change').append('');
  var money = parseFloat($('#total-money').val());

  if (isNaN(money)) {
    $('#total-money').val(0.1);
  } else {
    var totalMoney = parseFloat(money) + 0.1;
    $('#total-money').val(totalMoney.toFixed(2));
  }
}

function addNickel() {

  $('#total-change').empty();
  $('#total-change').append('');
  var money = parseFloat($('#total-money').val());

  if (isNaN(money)) {
    $('#total-money').val(0.05);
  } else {
    var totalMoney = parseFloat(money) + 0.05;
    $('#total-money').val(totalMoney.toFixed(2));
  }
}


//make change
function makeChange() {

  alert("Grab your change!");


  $('#select-item').val('');

  $('#total-change').val('');

  $('#total-money').val('');

}






$(document).ready(function() {

  $(document).on("click", ".vendingMachine-card", onVendingCardClick);
  $(document).on("click", "#make-purchase", makePurchase);
  $(document).on("click", "#change-button", makeChange);
  ds.getItems(loadItems, logError);




});
