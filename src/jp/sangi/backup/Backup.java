package jp.sangi.backup;

public class Backup {
	
	private int s,T, minT, minTAll[];
	private double h,p;
	private double result[], resultAll[][];
	private double minCT, minCTAll[];
	private double alpha = 0, beta = 0;
	private String title;
	private int n;
	
	public Backup(int s, double h, double p, int T) {
		this.s = s;
		this.h = h;
		this.p = p;
		this.T = T;
		this.result = new double[T+1];
		this.minCT = 100000000;
		this.title = "Geometric Distribution";
		this.n = 1;
		this.resultAll = new double[10][T+1]; //0->h:10, 9->h:100
		this.minCTAll = new double[10];
		this.minTAll = new int[10];
	}
	
	public Backup(int s, double h, double p, int T, double alpha, double beta) {
		this.s = s;
		this.h = h;
		this.p = p;
		this.T = T;
		this.result = new double[T+1];
		this.minCT = 100000000;
		this.alpha = alpha;
		this.beta = beta;
		this.title = "Wiebull Distribution";
		this.n = 1;
		this.resultAll = new double[10][T+1]; //0->h:10, 9->h:100
		this.minCTAll = new double[10];
		this.minTAll = new int[10];
	}

	public Backup(int s, double h, double p, int T, int n) {
		this.s = s;
		this.h = h;
		this.p = p;
		this.T = T;
		this.result = new double[T+1];
		this.minCT = 100000000;
		this.title = "Geometric Distribution";
		this.n = n;
		this.resultAll = new double[10][T+1]; //0->h:10, 9->h:100
		this.minCTAll = new double[10];
		this.minTAll = new int[10];
	}

	public int[] getMinTAll() {
		return minTAll;
	}

	public double[] getMinCTAll() {
		return minCTAll;
	}

	public double[][] getResultAll() {
		return resultAll;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	
	public int getN() {
		return n;
	}

	public String getTitle() {
		return title;
	}

	public double[] getResult() {
		return result;
	}

	public int getS() {
		return s;
	}

	public int getT() {
		return T;
	}

	public double getMinCT() {
		return minCT;
	}

	public int getMinT() {
		return minT;
	}

	public double getH() {
		return h;
	}

	public double getP() {
		return p;
	}

	public void computeBackup(){
		//Tが１以上なので、１から開始
		double ct;
		for(int i = 1; i <= T;  i++){
			ct = (h * (1-Math.pow(1-p, i))/p+s)/i-h*Math.pow(1-p, i);
			if(ct < minCT){
				minT = i;
				minCT = ct;
			}
			result[i] = ct;
		}
	}
	
	public void computeBackupAll(){
		//Tが１以上なので、１から開始
		double ct, h = this.h;
		for(int j = 0; j < 10; j++){
			minCTAll[j] = 1000000;
			for(int i = 1; i <= T;  i++){
				ct = (h * (1-Math.pow(1-p, i))/p+s)/i-h*Math.pow(1-p, i);
				if(ct < minCTAll[j]){
					minTAll[j] = i;
					minCTAll[j] = ct;
				}
				resultAll[j][i] = ct;
			}
			h += 100000;
		}
		
	}
	
	public void computeBackup_Weibull(){
		//Tが１以上なので、１から開始
		double ct, prob, tmp_ht = 0, ht = 0;
		for(int i = 1; i <= T;  i++){
			prob = 1-Math.exp(-Math.pow(((i+0.5)/beta), alpha));
			prob -= 1-Math.exp(-Math.pow(((i-0.5)/beta), alpha));
			ht = i * h * prob + tmp_ht;
			tmp_ht = ht;
			ct = (ht + s)/i;
			if(ct < minCT){
				minT = i;
				minCT = ct;
			}
			result[i] = ct;
		}
	}
	
	public void computeBackup_Weibull_All(){
		//Tが１以上なので、１から開始
		double ct, prob, h = this.h, ht = 0;
		for(int j = 0; j < 10; j++){
			minCTAll[j] = 1000000;
			double tmp_ht = 0;
			for(int i = 1; i <= T;  i++){
				prob = 1-Math.exp(-Math.pow(((i+0.5)/beta), alpha));
				prob -= 1-Math.exp(-Math.pow(((i-0.5)/beta), alpha));
				ht = i * h * prob + tmp_ht;
				tmp_ht = ht;
				ct = (ht + s)/i;
				if(ct < minCTAll[j]){
					minTAll[j] = i;
					minCTAll[j] = ct;
				}
				resultAll[j][i] = ct;
			}
			h += 100000;
		}
		
	}

	public void computeBackup_multi_min(){
		//Tが１以上なので、１から開始
		double ct, ht, h = this.h;;
		for(int i = 1; i <= T;  i++){
			//ht = (i*Math.pow(1-p, n*(i+1))-(i+1)*Math.pow(1-p, n*i)+1)*h*p/(Math.pow(1-Math.pow(1-p, n), 2));
			//System.out.println(ht);
			ht = 0;
			for(int j = 1; j <= i; j++){
				ht += h * j * (1-Math.pow(1-p, n)) * Math.pow(1-p, (j-1)*n);
			}
			//System.out.println(ht);
			//System.out.println("*********");
			ct = (ht + s)/i;
			if(ct < minCT){
				minT = i;
				minCT = ct;
			}
			result[i] = ct;
		}
	}
	
	public void computeBackup_multi_max(){
		//Tが１以上なので、１から開始
		double ct, ht, h = this.h;;
		for(int i = 1; i <= T;  i++){
			ht = 0;
			for(int j = 1; j <= i; j++){
				ht += h * j * ( Math.pow(1 - Math.pow(1-p, j), n) - Math.pow(1 - Math.pow(1-p, j-1), n)); 
			}
			//System.out.println(ht);
			//System.out.println("*********");
			ct = (ht + s)/i;
			if(ct < minCT){
				minT = i;
				minCT = ct;
			}
			result[i] = ct;
		}
	}
	
	public void computeBackup_multi_minAll(){
		//Tが１以上なので、１から開始
		double ct, ht, h = this.h;;
		for(int j = 0; j < 10; j++){
			minCTAll[j] = 1000000;
			for(int i = 1; i <= T;  i++){
				ht = 0;
				for(int k = 1; k <= i; k++){
					ht += h * k * (1-Math.pow(1-p, n)) * Math.pow(1-p, (k-1)*n);
				}
				ct = (ht + s)/i;
				if(ct < minCTAll[j]){
					minTAll[j] = i;
					minCTAll[j] = ct;
				}
				resultAll[j][i] = ct;
			}
			h += 100000;
		}
		
	}
	
	public void computeBackup_multi_maxAll(){
		//Tが１以上なので、１から開始
		double ct, ht, h = this.h;;
		for(int j = 0; j < 10; j++){
			minCTAll[j] = 1000000;
			for(int i = 1; i <= T;  i++){
				ht = 0;
				for(int k = 1; k <= i; k++){
					ht += h * k * ( Math.pow(1 - Math.pow(1-p, k), n) - Math.pow(1 - Math.pow(1-p, k-1), n)); 
				}
				
				ct = (ht + s)/i;
				if(ct < minCTAll[j]){
					minTAll[j] = i;
					minCTAll[j] = ct;
				}
				resultAll[j][i] = ct;
			}
			h += 100000;
		}
		
	}
}
