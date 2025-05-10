<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Update Item DETAILS</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
  <link rel="stylesheet" href="css/add-item.css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="text">
    Update Item DETAILS
  </div>
  <form action="ItemController">
    <div class="form-row">
      
      <div class="input-data">
        <input type="text" name="type" value="${existedItem.type}" required>
        <div class="underline"></div>
        <label>Type</label>
      </div>
      <div class="input-data">
        <input type="text" name="model" value="${existedItem.model}" required>
        <div class="underline"></div>
        <label>Model</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="text" name="description" value="${existedItem.description}" required>
        <div class="underline"></div>
        <label>Description</label>
      </div>

    </div>
    
    <input type="hidden" name="id" value="${existedItem.itemId}">
    <input type="hidden" name="action" value="Update_ITEM_DETAILS">
    
    <input type="submit" value="Update_ITEM_DETAILS" class="button">
  </form>

  <p class="back">
    <a href="ItemController" >Back To Items</a>
  </p>
</div>
<!-- partial -->

</body>
</html>