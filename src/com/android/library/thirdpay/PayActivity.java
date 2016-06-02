package com.android.library.thirdpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import com.android.library.R;
import com.android.library.thirdpay.alipay.PayResult;
import com.android.library.thirdpay.alipay.SignUtils;
import com.android.library.ui.base.BaseSubActivity;
import com.android.library.ui.dialog.ConfirmDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends BaseSubActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

	// 商户PID
	public static final String PARTNER = "2088221880658293";
	// 商户收款账号
	public static final String SELLER = "steersgman@gmail.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOF+Eeok6Ich/eCjAXfKUdCx6stWze7U8+YpcSC+bxBa6OclRMoilAP4A4beONQPMCf+BZZC/ChuINMvxAZ1gVdGl/lX2+sfogXXDfkNBArsZ7yEwj9+0PRKaQ2/FhRFkhvpMK+wjQ20rnKhEhQb5xfKJFdzxvNAnmKk1aPs/oT5AgMBAAECgYBLVXoYtqH025dc4DiSU8aHqZkeu+5q9zxQzdGcteKXHTgoWRwlR+4ZcVeETGPB/R0Cj2xAajqiK0DOLWwZGFLQO/cSVsyLUHQnRYU9KSTOhBm3Emhf5UQizqhi8BTOHDr+8tiCCks8CD9ETtzBxveAz+ayfP5eZrBVA/yEIjjoLQJBAPq6GvX734qzg7RdRWSDOSyVSy8lhz/NLdIamZTmOBPI1iGBdS5stPH+MxkqXHcAsG2i0MLYmJ/KA/RQhF+6LS8CQQDmPBqfM7ONLJ7O+uRu92e/y8O0TUSUafnEEcaxv7riEVpKOOG7ZDFLNuOAFRNTXgRn41+SoGvNqRPGKu2wEvZXAkAQtAFDzenQhbEKfQQ4c/2FGNUPaQKfn32j952vq76kosaRKNyzoQ5U6Mhirs3GJ8eZJjWeJWvViVHGHu8SqYaFAkA/vL3l+usnFwYz6LMaoTVAnIlmPcnRiHETvZQTGzACa6liSNL9DKS03NIoYC1EdA+mLwUDU5PkkH0f7Dx29RjrAkEAp63lf+5NDasn8BUyz5rlYVZ5+JwbTWNTo4hnooXLgCCZu7sxq3SyB0y8ztCUnnCDSewUCZ/rnFptyPtIK1xLbQ==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhfhHqJOiHIf3gowF3ylHQserLVs3u1PPmKXEgvm8QWujnJUTKIpQD+AOG3jjUDzAn/gWWQvwobiDTL8QGdYFXRpf5V9vrH6IF1w35DQQK7Ge8hMI/ftD0SmkNvxYURZIb6TCvsI0NtK5yoRIUG+cXyiRXc8bzQJ5ipNWj7P6E+QIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

		
    private static final int PAY_ALI = 1;
    private static final int PAY_WECHAT = 0;
    //private TreatResp treat;
    private ListView listView;
    //private PayAdapter adapter;
    private TextView priceTv;
    private RadioGroup payWayRg;
    private Button payBtn;

    private View IconLl;

    private double mDonateValue;
    private String mMessage;
    
    //private PayBean payBean;
    // 1 支付宝   0 微信
    private int payWay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        setTitle(R.string.pay_donate_pay_title);
        setActionBarBackground(R.drawable.lib_drawable_common_actionbar_background);
        initUI();
    }

    @Override
    protected void setBackView(TextView back) {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDlg();
            }
        });
    }

    @Override
    protected boolean initIntent() {
        /*payBean = (PayBean) getIntent().getSerializableExtra(ActivityUtil.INTENT_BEAN);
        if (payBean == null || payBean.isEmptyItem()) {
            return false;
        }*/
    	Bundle bundle = getIntent().getExtras();
    	if(bundle != null){
    		mMessage = bundle.getString(DonateActivity.EXTRA_DONATE_MESSAGE);
    		mDonateValue = bundle.getDouble(DonateActivity.EXTRA_DONATE_PAY);
    	}
        return true;
    }

    private void initUI() {
        // 详情列表
        //float totalPrice = 0;
        /*adapter = new PayAdapter(activity);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        adapter.setItems(payBean.items);

        for (PayItem item : payBean.items) {
            totalPrice += item.price;
        }*/
        // 总价格
        priceTv = (TextView) findViewById(R.id.priceTv);
        priceTv.setText(getString(R.string.pay_total_price, String.format("%.2f", mDonateValue)));
        //支付方式
        payWayRg = (RadioGroup) findViewById(R.id.payWayRg);
        payWayRg.setOnCheckedChangeListener(this);
        //确认支付
        payBtn = (Button) findViewById(R.id.payBtn);
        payBtn.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.weChatRb) {
            payWay = PAY_WECHAT;
        } else if (checkedId == R.id.aliPayRb) {
            payWay = PAY_ALI;
        }
    }

    @Override
    public void onBackPressed() {
        showConfirmDlg();
    }

    private void showConfirmDlg(){
        showConfirmDialog(R.string.pay_donate_confirm_content, R.string.pay_donate_confirm_title, R.string.pay_donate_confirm_ok, R.string.pay_donate_confirm_cancel, new ConfirmDialog.OnResultListener() {
            @Override
            public void onConfirm() {
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.payBtn) {
            switch (payWay) {
                case PAY_WECHAT:
                    doWeChatPay();
                    break;
                case PAY_ALI:
                    doAliPay();
                    break;
            }
        }
    }


    public void doAliPay() {
    	String orderInfo = getOrderInfo(getString(R.string.pay_donate_title), mMessage, String.valueOf(mDonateValue));
    	String sign = sign(orderInfo);
    	try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
        /*PayUtil.staryAliPay(activity, payBean.name, payBean.price, payBean.orderId, new AliPay.Callback() {
            @Override
            public void onSuccess() {
                ToastUtils.showToast("支付成功ali");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onPaying() {
                ToastUtils.showToast("支付处理中ali");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailed() {
                ToastUtils.showToast("支付失败ali");
            }
        });*/
    }

    private void doWeChatPay() {
        /*PayUtil.staryWeChatPay(activity, payBean.name, payBean.orderId, payBean.price, new WeChatPay.Callback() {
            @Override
            public void onFailed() {
                ToastUtils.showToast("支付失败onFailedWexin");

            }

            @Override
            public void onSuccess() {
                ToastUtils.showToast("支付成功Wexin");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onCancel() {
                ToastUtils.showToast("支付取消Wexin");
            }

            @Override
            public void onError() {
                ToastUtils.showToast("支付失败onErrorWexin");
            }
        });*/
    }
    
    private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayActivity.this, getString(R.string.pay_donate_result_success), Toast.LENGTH_SHORT).show();
					setResult(RESULT_OK);
					PayActivity.this.finish();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayActivity.this, getString(R.string.pay_donate_result_process), Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayActivity.this, getString(R.string.pay_donate_result_fail), Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	
    /**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
