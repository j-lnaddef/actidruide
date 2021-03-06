<#--
//-------------------------------------------------------------
// Projet MESR - ECN&CI
// Copyright (C) 2013 ACTIMAGE
// 
// Créé le : 14/11/2013
// Auteur  : Jean-Loup NADDEF
//-------------------------------------------------------------
-->
<#assign itemCount = 0/>
<#if parameters.list??>
    <@s.iterator value="parameters.list">
        <#assign itemCount = itemCount + 1/>
        <#if parameters.listKey??>
            <#assign itemKey = stack.findValue(parameters.listKey)/>
            <#else>
                <#assign itemKey = stack.findValue('top')/>
        </#if>
        <#if parameters.listValue??>
            <#assign itemValue = stack.findString(parameters.listValue)?default("")/>
            <#else>
                <#assign itemValue = stack.findString('top')/>
        </#if>
        <#if parameters.listCssClass??>
            <#if stack.findString(parameters.listCssClass)??>
              <#assign itemCssClass= stack.findString(parameters.listCssClass)/>
            <#else>
              <#assign itemCssClass = ''/>
            </#if>
        </#if>
        <#if parameters.listCssStyle??>
            <#if stack.findString(parameters.listCssStyle)??>
              <#assign itemCssStyle= stack.findString(parameters.listCssStyle)/>
            <#else>
              <#assign itemCssStyle = ''/>
            </#if>
        </#if>
        <#if parameters.listTitle??>
            <#if stack.findString(parameters.listTitle)??>
              <#assign itemTitle= stack.findString(parameters.listTitle)/>
            <#else>
              <#assign itemTitle = ''/>
            </#if>
        </#if>
        <#assign itemKeyStr=itemKey.toString() />
        <div class="element_checkbox">
            <input type="checkbox" name="${parameters.name?html}" value="${itemKeyStr?html}"
                   id="${parameters.name?html}-${itemCount}"<#rt/>
                <#if tag.contains(parameters.nameValue, itemKey)>
                   checked="checked"<#rt/>
                </#if>
                <#if parameters.disabled?default(false)>
                   disabled="disabled"<#rt/>
                </#if>
                <#if itemCssClass?if_exists != "">
                 class="${itemCssClass?html}"<#rt/>
                <#else>
                    <#if parameters.cssClass??>
                 class="${parameters.cssClass?html}"<#rt/>
                    </#if>
                </#if>
                <#if itemCssStyle?if_exists != "">
                 style="${itemCssStyle?html}"<#rt/>
                <#else>
                    <#if parameters.cssStyle??>
                 style="${parameters.cssStyle?html}"<#rt/>
                    </#if>
                </#if>
                <#if itemTitle?if_exists != "">
                 title="${itemTitle?html}"<#rt/>
                <#else>
                    <#if parameters.title??>
                 title="${parameters.title?html}"<#rt/>
                    </#if>
                </#if>
                <#include "/${parameters.templateDir}/defaut/css.ftl" />
                <#include "/${parameters.templateDir}/defaut/scripting-events.ftl" />
                <#include "/${parameters.templateDir}/defaut/common-attributes.ftl" />
                    />
            <label for="${parameters.name?html}-${itemCount}" class="checkboxLabel">${itemValue?html}</label>
        </div>
    </@s.iterator>
<#else>
    &nbsp;
</#if>
<input type="hidden" id="__multiselect_${parameters.id?html}" name="__multiselect_${parameters.name?html}"
       value=""<#rt/>
<#if parameters.disabled?default(false)>
       disabled="disabled"<#rt/>
</#if>
        />