<#import "layout/layout_login.ftl" as layout>
<#import "commun/composants.ftl" as composants>


<@layout.affiche "Connexion">
    <div id="connexion">
            <@s.form namespace="/" action="authentifier" method="post">
                <table>
                    <tr>
                        <td colspan="2">
                            <@composants.messagesErreur />
                        </td>
                    </tr>
                    <tr>
                        <td class="largeur120"><label for="login" class="gras">Login</label></td>
                        <td><@s.textfield name="login" id="login" cssClass="largeur160" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><br/></td>
                    </tr>
                    <tr>
                        <td class="largeur120"><label for="motDePasse" class="gras">Mot de passe</label></td>
                        <td><@s.password name="motDePasse" id="motDePasse" cssClass="largeur160" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><br/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <@s.submit cssClass="bouton" value="Entrer!" />
                        </td>
                    </tr>
                </table>
            </@s.form>
            <script type="text/javascript">
                <#if fieldErrors?? && fieldErrors["motDePasse"]??>
                    $("#connexion input[name='motDePasse']").focus();
                <#else>
                    $("#connexion input[name='login']").focus();
                </#if>
            </script>
    </div>
</@layout.affiche>