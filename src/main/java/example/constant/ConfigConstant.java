package example.constant;

import org.apache.commons.lang.StringUtils;

/**
 * @version 2.0
 * @author chen_xi description: 服务地址配置样例
 */
public class ConfigConstant {

	// 模拟环境域名
	public static final String host = "https://smlopenapi.esign.cn";
	// 生产环境域名
//	public static final String host = "https://openapi.esign.cn";

	// 项目Id(应用Id）
	public static final String PROJECT_ID = "";
	// 项目密钥(应用密钥）
	public static final String PROJECT_SECRET = "";

	// ------Token相关地址------
	// 获取Token
	public static final String getToken_URL(String appId, String secret) {
		return host + "/v1/oauth2/access_token?appId=" + appId + "&secret=" + secret + "&grantType=client_credentials";
	}

	// 刷新Token
	public static final String refreshToken_URL(String appId, String refreshToken) {
		return host + "/v1/oauth2/refresh_token?appId=" + appId + "&refreshToken=" + refreshToken
				+ "&grantType=refresh_token";
	}

	// ------账号相关地址------

	// ---个人账号---

	// 创建个人账号
	public static final String createPerAcc_URL() {
		return host + "/v1/accounts/createByThirdPartyUserId";
	}

	// 修改个人账号(根据账号ID)
	public static final String modifyPerAccById_URL(String accountId) {
		return host + "/v1/accounts/" + accountId;
	}

	// 修改个人账号（根据第三方用户ID）
	public static final String modifyPerAccByThirdId_URL(String thirdPartyUserId) {
		return host + "/v1/accounts/updateByThirdId?thirdPartyUserId=" + thirdPartyUserId;
	}

	// 查询个人账号(根据账号ID)
	public static final String queryAccById_URL(String accountId) {
		return host + "/v1/accounts/" + accountId;
	}

	// 查询个人账号(根据第三方用户ID)
	public static final String queryAccByThirdId_URL(String thirdPartyUserId) {
		return host + "/v1/accounts/getByThirdId?thirdPartyUserId=" + thirdPartyUserId;
	}

	// 注销个人账号(根据账号ID)
	public static final String logoutAccById_URL(String accountId) {
		return host + "/v1/accounts/" + accountId;
	}

	// 注销个人账号(根据第三方用户ID)
	public static final String logoutAccByThirdId_URL(String thirdPartyUserId) {
		return host + "/v1/accounts/deleteByThirdId?thirdPartyUserId=" + thirdPartyUserId;
	}

	// ---机构账号---

	// 创建机构账号
	public static final String createOrgAcc_URL() {
		return host + "/v1/organizations/createByThirdPartyUserId";
	}

	// 修改机构账号(根据账号ID)
	public static final String modifyOrgAccById_URL(String orgId) {
		return host + "/v1/organizations/" + orgId;
	}

	// 修改机构账号(根据第三方用户ID)
	public static final String modifyOrgAccByThirdId_URL(String thirdPartyUserId) {
		return host + "/v1/organizations/updateByThirdId?thirdPartyUserId=" + thirdPartyUserId;
	}

	// 查询机构账号(根据账号ID)
	public static final String queryOrgAccById_URL(String orgId) {
		return host + "/v1/organizations/" + orgId;
	}

	// 查询机构账号(根据第三方用户ID)
	public static final String queryOrgAccByThirdId_URL(String thirdPartyUserId) {
		return host + "/v1/organizations/getByThirdId?thirdPartyUserId=" + thirdPartyUserId;
	}

	// 注销机构账号(根据账号ID)
	public static final String logoutOrgAccById_URL(String orgId) {
		return host + "/v1/organizations/" + orgId;
	}

	// 注销机构账号(根据第三方用户ID)
	public static final String logoutOrgAccByThirdId_URL(String thirdPartyUserId) {
		return host + "/v1/organizations/deleteByThirdId?thirdPartyUserId=" + thirdPartyUserId;
	}

	// ---邀请开通服务---
	// 创建邀请任务
	public static final String create_invite_URL() {
		return host + "/v1/invitations";
	}

	// ------文件相关地址------

	// 获取文件直传地址
	public static final String fileUpload_URL() {
		return host + "/v1/files/getUploadUrl";
	}

	// 文件模板创建待签文件
	public static final String createFileByTemplate_URL() {
		return host + "/v1/files/createByTemplate";
	}

	// 获取文件下载地址(根据文件Id)
	public static final String fileDownloadByFileId_URL(String fileId) {
		return host + "/v1/files/" + fileId;
	}

	// -----文件模板管理-------

	// 通过上传方式创建模板
	public static final String createTemplateByUpload_URL() {
		return host + "/v1/docTemplates/createByUploadUrl";
	}

	// 添加输入项组件
	public static final String addInputNodes_URL(String templateId) {

		return host + "/v1/docTemplates/" + templateId + "/components";
	}

	// 删除输入项组件
	public static final String deleteInputNodes_URL(String templateId, String ids) {
		return host + "/v1/docTemplates/" + templateId + "/components/" + ids;
	}

	// 查询模板详情
	public static final String queryInputNodes_URL(String templateId) {
		return host + "/v1/docTemplates/" + templateId;
	}

	// ------签署相关地址------

	// ---签署流程---
	// 创建签署流程
	public static final String createFlows_URL() {
		return host + "/v1/signflows";
	}

	// 查询签署流程
	public static final String queryFlows_URL(String flowId) {
		return host + "/v1/signflows/" + flowId;
	}

	// 开启签署流程
	public static final String startFlows_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/start";
	}

	// 撤销签署流程
	public static final String revokeFlows_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/revoke";
	}

	// 归档签署流程
	public static final String archiveFlows_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/archive";
	}

	// ---流程文档(请求方式决定增删或者下载)---
	public static final String aboutDocument_URL(String flowId, String fileIds) {
		String url = host + "/v1/signflows/" + flowId + "/documents";
		if (StringUtils.isNotBlank(fileIds)) {
			url += "?fileIds=" + fileIds;
		}
		return url;
	}

	// ---流程附件(请式求方决定增删查)---
	public static final String aboutAttachments_URL(String flowId, String fileIds) {
		String url = host + "/v1/signflows/" + flowId + "/attachments";
		if (StringUtils.isNotBlank(fileIds)) {
			url += "?fileIds=" + fileIds;
		}
		return url;
	}

	// ---流程签名域---

	// 获得流程签名域列表
	public static final String getSignFields_URL(String flowId, String accountId, String signfieldIds) {
		String url = host + "/v1/signflows/" + flowId + "/signfields";
		boolean flag = false;
		if (StringUtils.isNotBlank(accountId)) {
			url += "?accountId=" + accountId;
			flag = true;
		}
		if (StringUtils.isNotBlank(signfieldIds)) {
			if (flag) {
				url += "&";
			} else {
				url += "?";
			}
			url += "signfieldIds=" + signfieldIds;
		}
		return url;
	}

	// 添加流程签名域(对接平台自动盖章签名域)
	public static final String addAutoSignfieldsForPlatform_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/signfields/platformSign";
	}

	// 添加流程签名域(用户自动盖章签名域)
	public static final String addAutoSignfieldsForPerson_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/signfields/autoSign";
	}

	// 添加流程签名域(用户手动盖章签名域)
	public static final String addHandSignfieldsForPerson_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/signfields/handSign";
	}

	// 删除流程签名域
	public static final String deleteSignfields_URL(String flowId, String signfieldIds) {
		return host + "/v1/signflows/" + flowId + "/signfields?signfieldIds=" + signfieldIds;
	}

	// ---流程签署人---

	// 获取流程签署人列表
	public static final String getSignersList_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/signers";
	}

	// 流程签署人催签
	public static final String urgeSgin_URL(String flowId) {
		return host + "/v1/signflows/" + flowId + "/signers/rushsign";
	}

	// 获取签署地址
	public static final String Sign_URL(String flowId, String accountId, String organizeId, String urlType) {
		String url = host + "/v1/signflows/" + flowId + "/executeUrl?accountId=" + accountId;
		if (StringUtils.isNotBlank(organizeId)) {
			url += "&organizeId=" + organizeId;
		}
		if (StringUtils.isNotBlank(urlType)) {
			url += "&urlType=" + urlType;
		}
		return url;
	}

	// ---辅助工具---

	// 查询流程文档关键字坐标
	public static final String getKeywordsPosition_URL(String flowId, String fileId) {
		StringBuffer keywordsPosition = new StringBuffer();
		keywordsPosition.append(host).append("/v1/signflows/");
		keywordsPosition.append(flowId);
		keywordsPosition.append("/documents/");
		keywordsPosition.append(fileId);
		keywordsPosition.append("/searchWordsPosition");
		return keywordsPosition.toString();
	}

}
