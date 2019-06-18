function DataService() {
  var self = this;
  self.getItems = function(callback, errorCallback) {
    console.log(2, "Just called items");
    $.ajax({
      url: "http://localhost:8080/items",
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
      success: callback,
      error: errorCallback
    });
  };

  self.vendItem = function(money, itemId, callback, errorCallback) {
      alert("You are about to buy " + itemId);
      $.ajax({
          url: "http://localhost:8080/money/" + money + "/item/" + itemId,
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
      success:callback,
      error: errorCallback
    });
     }
   }
