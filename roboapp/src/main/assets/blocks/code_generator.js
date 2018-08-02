Blockly.JavaScript['movement_robot'] = function(block) {
  var number_time = block.getFieldValue('time');
  var number_lin_angle = block.getFieldValue('lin_angle');
  var number_lin_speed = block.getFieldValue('lin_speed');
     
    // Function,Time,Angular_speed,Linear_angle,Linear_speed
    
    var code = "func 1 " + number_time+ " 0 " + number_lin_angle + " " +number_lin_speed + " =";
  return code;
};

Blockly.JavaScript['movement_stop'] = function(block) {
    
    //Function
    
  var code = "func 2 =";
  return code;
};

Blockly.JavaScript['movement_speed'] = function(block) {
  var number_lin_speed = block.getFieldValue('lin_speed');
  var number_lin_angle = block.getFieldValue('lin_angle');
  
    
    //Function,time,Angular_speed,Linear_angle,Linear_speed
    
     var code = "func 3 0 0 " + number_lin_angle + " " +number_lin_speed + " =";
  return code;
};

Blockly.JavaScript['control_exit'] = function(block) {
  
    
    //Function
    
  var code = "func 4 =";
  return code;
};

Blockly.JavaScript['control_wait'] = function(block) {
  var number_time = block.getFieldValue('time');
    
    
    //Function,Wait_for_time
    
   var code = "func 5 "+ number_time+" =";
  return code;
};


//TO BE EDITED
Blockly.JavaScript['loop_repeat'] = function(block) {
  var number_times = block.getFieldValue('times');
  var statements_do = Blockly.JavaScript.statementToCode(block, 'Do');
  // TODO: Assemble JavaScript into code variable.
  var code = "func 6 ,"+number_times + " =" +statements_do;
  return code;
};

Blockly.JavaScript['app_headlights'] = function(block) {
  var value_name = Blockly.JavaScript.valueToCode(block, 'NAME', Blockly.JavaScript.ORDER_ATOMIC);

    //Function,Headlights_value
    
    var code;
    
    if (value_name == "true")
    code = "func 7 1 =";
    else 
    code = "func 7 0 =";
    
  return code;
};

Blockly.JavaScript['app_backlight'] = function(block) {
  var value_name = Blockly.JavaScript.valueToCode(block, 'NAME', Blockly.JavaScript.ORDER_ATOMIC);
  
    
    //Function,TailLights_value
    
    var code;
    
    if (value_name == "true")
    code = "func 8 1 =";
    else 
    code = "func 8 0 =";
    
  return code;
};

Blockly.JavaScript['rotation_rotate'] = function(block) {
  var number_time = block.getFieldValue('time');
  var number_speed = block.getFieldValue('speed');
    
    
  //Function,Angular_speed,Linear_angle,Linear_speed
    
   var code = "func 9 " + number_time +" " + number_speed + " 0 0 =";
  return code;
};

Blockly.JavaScript['rotation_strafe'] = function(block) {
  var number_time = block.getFieldValue('time');
  var number_ang_speed = block.getFieldValue('ang_speed');
  var number_lin_angle = block.getFieldValue('lin_angle');
  var number_lin_speed = block.getFieldValue('lin_speed');
    
      // Function,Time,Angular_speed,Linear_angle,Linear_speed
    
  var code = "func 10 "+ number_time+ " " + number_ang_speed +" " + number_lin_angle + " " +number_lin_speed + " =";
  return code;
};

