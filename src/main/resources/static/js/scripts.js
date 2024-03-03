
function iframeLoaded()
{
    var iFrameID = document.getElementById('search');
    if(iFrameID)
    {
            iFrameID.height = iFrameID.contentWindow.document.body.scrollHeight + 256 +"px";
    }
    var searchContent = window.frames['search'].document.getElementById('input');         
}

function updateCheckboxes(first, second)
{ 
    if (first.checked)
    {
        second.checked = false;
    }
    else if (second.checked)
    {
        first.checked = false;
        second.checked = true;
    }
    else
    {       
        first.checked = false;        
        second.checked = false;                 
    }
    updateForm();
}
function updateForm()
{
    var form = document.getElementById('voting');
    if (form)
    {
        form.submit();
    }
}