const form=document.getElementById("form");
const fullName=document.getElementById("fullName");
const email=document.getElementById("email");
const phoneNumber=document.getElementById("phoneNumber");

const nameRegex=/[A-Z][a-zA-Z ]*/;
const emailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
const phoneNumberRegex = /(\+91)?[976]\d{9}/;

form.addEventListener("submit",e=>{
    e.preventDefault();

    if(validateInput()){
        form.submit();
    }
})

const setError=(element,message)=>{
    const parentDiv=element.parentElement;
    const errorDiv=parentDiv.querySelector(".error");
    errorDiv.innerText=message;
}
const setSuccess=(element)=>{
    const parentDiv=element.parentElement;
    const errorDiv=parentDiv.querySelector(".error");
    errorDiv.innerText="";
}

const validateInput=()=>{
    const fullNameValue=fullName.value.trim();
    const emailValue=email.value.trim();
    const phoneNumberValue=phoneNumber.value.trim();
    let isValid = true;

     if(fullNameValue===""){
           setError(fullName,"Name is required");
           isValid = false;
       }else if(!(nameRegex.test(fullNameValue))){
           setError(fullName,"First character should be in uppercase");
           isValid = false;
      }else if((fullNameValue.length<3) || (fullNameValue.length>50) ){
           setError(fullName,"Name length should be >3 and <50 ");
           isValid = false;
       }else{
           setSuccess(fullName);
       }

       if(emailValue===""){
           setError(email,"Email  is required");
           isValid = false;
       }else if(!(emailRegex.test(emailValue))){
           setError(email,"Domain should be gmail.com, no spaces in between, and only '_', '.', '%', '+', and '-' are allowed.");
           isValid = false;
      }else{
           setSuccess(email);
      }

      if(phoneNumberValue===""){
           setError(phoneNumber,"Phone number  is required");
           isValid = false;
       }else if(!(phoneNumberRegex.test(phoneNumberValue))){
           setError(phoneNumber,"Phone number must start with 9, 7, or 6 and must contain exactly 10 digits.");
           isValid = false;
      }else{
           setSuccess(phoneNumber);
      }
      return isValid;
}
