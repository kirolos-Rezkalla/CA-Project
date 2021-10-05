import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;


public class DoubleMcHarvard {
	public static Integer[] instructionMemory = new Integer[1024];
	public static Byte[] dataMemory = new Byte[2048] ;
	public static Short pc;
	public static Hashtable<Integer,Byte> registers = new Hashtable<Integer,Byte>();
	public static Integer tobedecoded;
	public static int[] tobeexecuted;
	
	
public static void readIntoArray(String s) throws IOException{
	File file = new File("src\\"+s+".txt");
	BufferedReader br = new BufferedReader(new FileReader(file));
	  
	String st;
	while ((st = br.readLine()) != null){
		String[] line = st.split(" ");
		insertIntoInstructionMemory(line);
	}
	pc=0;
}
public static void insertIntoInstructionMemory(String[] line){
	int index = 0;
    String result = "";
    int finalResult = 0;
	for(int i = 0; i<instructionMemory.length; i++){
		if(instructionMemory[i] == null){
			index = i;
			break;
		}
	}
	
		int reg1 = 0;
		int reg2 = 0;
		switch(line[0]){
		  case "ADD": result += "0000";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  reg2 = Integer.parseInt((line[2].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg2));
		  break;
		  
		  case "SUB": result += "0001";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  reg2 = Integer.parseInt((line[2].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg2));
		  break;
		  
		  case "MUL": result += "0010";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  reg2 = Integer.parseInt((line[2].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg2));
		  break;
		  
		  case "MOVI": result += "0011";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		  
		  case "BEQZ": result += "0100";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		  
		  case "ANDI": result += "0101";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		  
		  case "EOR": result += "0110";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  reg2 = Integer.parseInt((line[2].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg2));
		  break;
		  
		  case "BR": result += "0111";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  reg2 = Integer.parseInt((line[2].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg2));
		  break;
		  
		  case "SAL": result += "1000";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		  
		  case "SAR": result += "1001";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		  
		  case "LDR": result += "1010";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		
		  case "STR": result += "1011";
		  reg1 = Integer.parseInt((line[1].substring(1)));
		  result += ensureSix(Integer.toBinaryString(reg1));
		  result += ensureSix(Integer.toBinaryString(Integer.parseInt(line[2])));
		  break;
		
	}
	finalResult = Integer.parseInt(result,2);
	instructionMemory[index] = finalResult;
}
public static String ensureFour(String check){
	while(check.length() < 4){
		check = "0" + check;
	}
	if(check.length()>4) {
		check=check.substring(check.length()-4,check.length());
	}
	return check;
}
public static String ensureSix(String check){
	while(check.length() < 6){
		check = "0" + check;
	}
	if(check.length()>6) {
		check=check.substring(check.length()-6,check.length());
	}
	return check;
}
public static String ensureEight(String check){
	while(check.length() < 8){
		check = "0" + check;
	}
	if(check.length()>8) {
		check=check.substring(check.length()-8,check.length());
	}
	return check;
}
public static String ensureSixteen(String check){
	while(check.length() < 16){
		check = "0" + check;
	}
	if(check.length()>16) {
		check=check.substring(check.length()-16,check.length());
	}
	return check;
}

public static void fetch(){
		//int pc = pc;
		System.out.println("Fetching started");
		if(instructionMemory[pc]!=null) {
        int instruction = instructionMemory[pc];
        pc++;
        tobedecoded= instruction;
        System.out.println("The instruction fetched in this cycle is  "+ensureSixteen(Integer.toBinaryString(instruction)));
		}
		else {
			pc++;
			 System.out.println("No instruction has been fetched  ");
		}
		System.out.println("Fetching ended");
		
}
        
public static void decode(int instruction) {
	System.out.println("Decoding started");
	int[] result = new int[4];
			
    int opcode = 0;  // bits15:12
    int rt = 0;      // bits11:6
    int rs = 0;      // bits5:0
    int imm = 0;     // bits5:0
    
    int temp = instruction >> 12;
	opcode = temp & 0x000F;
	result[0] = opcode;
    
    temp = instruction  >> 6;
    rt = temp & 0x003F;
    result[1] = rt;
    
    temp = instruction;
    rs = temp & 0x003F;
    result[2] = rs;
    
    temp = instruction;
    imm = temp & 0x003F;
    result[3] = imm;
    
    tobeexecuted = result;
    //so as not to repeat decoding for and instruction if nothing was fetched last cycle
    tobedecoded=null;
    System.out.println("The instruction decoded in this cycle is  "+ensureSixteen(Integer.toBinaryString(instruction)));
    System.out.println("Decoding ended");
 
}

public static void execute(int[] result){
	System.out.println("Executing started");
	int opcode = result[0];
	int rt = result[1];
	int rs = result[2];
	int imm = result[3];
	Byte immPrint;
	String status="000";
	System.out.println("The instruction executed in this cycle is  "+ensureFour(Integer.toBinaryString(opcode))+ensureSix(Integer.toBinaryString(rt))+ensureSix(Integer.toBinaryString(rs)));
	System.out.println("OPCODE entered to execute is : "+opcode);
	System.out.println("Source register entered to execute is : "+rs +" and its value is "+registers.get(rs));
	System.out.println("Target register entered to execute is : "+rt +" and its value is "+registers.get(rt));
	 if(imm>31) {
		 imm=imm|0xFFFFFFC0;
		immPrint = (byte) imm;
	  }
	  else
	  immPrint = (byte) imm;
	System.out.println("Immediate value entered to execute is : "+immPrint);
	switch(opcode){
	  case 0: 
		  Byte add = (byte) (registers.get(rs) + registers.get(rt));
		  int addint = (registers.get(rs) + registers.get(rt));
		  int masked = (registers.get(rs)&(0x000000FF)) + (registers.get(rt)&(0x000000FF));
		  if(registers.get(rt)!=null)
		      registers.replace(rt, add);
			  else
				  registers.put(rt,add); 
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      //C
	      if(masked>255)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      //V
	      if(addint-add!=0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      //N
	      if(add<0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      //S
	      if((status.charAt(4)==1&&status.charAt(5)==0)||(status.charAt(5)==1&&status.charAt(4)==0))
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      //Z
	      if(add==0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	    	  
	      
	      break;
	  
	  case 1: 
		  Byte sub = (byte) (registers.get(rt) - registers.get(rs));
		  int subint = registers.get(rt) - registers.get(rs);
		  int maskedsub = (registers.get(rs)&(0x000000FF)) - (registers.get(rt)&(0x000000FF));
		  if(registers.get(rt)!=null)
	      registers.replace(rt, sub);
		  else
			  registers.put(rt,sub);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      if(maskedsub>255)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      if(subint-sub!=0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      if(sub<0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      if((status.charAt(4)==1&&status.charAt(5)==0)||(status.charAt(5)==1&&status.charAt(4)==0))
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      if(sub==0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      break;
	  
	  case 2: 
		  Byte mul = (byte) (registers.get(rt) * registers.get(rs));
		  int maskedmul = (registers.get(rs)&(0x000000FF)) * (registers.get(rt)&(0x000000FF));
		  if(registers.get(rt)!=null)
		      registers.replace(rt, mul);
			  else
				  registers.put(rt,mul);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      //C
	      if(maskedmul>255)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      //V
	      status=status+"0";
	      //N
	      if(mul<0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      
	      //S
	      status=status+"0";
	      //Z
	      if(mul==0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      
	      break;
	  
	  case 3: 
		  Byte movi;
		  if(imm>31) {
			 imm=imm|0xFFFFFFC0;
			 movi = (byte) imm;
		  }
		  else
		  movi = (byte) imm;
		  if(registers.get(rt)!=null)
		      registers.replace(rt, movi);
			  else
				  registers.put(rt,movi);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      status=status+"00000";
	      break;
	  
	  case 4: 
		  Byte nuAdbyte;
		  if(imm>31) {
				 imm=imm|0xFFFFFFC0;
				  nuAdbyte = (byte) imm;
			  }
			  else
			  nuAdbyte = (byte) imm;
		  if(registers.get(rt) == 0){
			  Short newAddress = (short) (pc + nuAdbyte);
			  pc=newAddress;
			  tobedecoded=null;
			  tobeexecuted=null;
		  }
		  status=status+"00000";
	      break;
	  
	  case 5: 
		  Byte nuAndbyte;
		  if(imm>31) {
				 imm=imm|0xFFFFFFC0;
				  nuAndbyte = (byte) imm;
			  }
			  else
			  nuAndbyte = (byte) imm;
		  Byte andi = (byte) (registers.get(rt) & nuAndbyte);
		  if(registers.get(rt)!=null)
		      registers.replace(rt, andi);
			  else
				  registers.put(rt,andi);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      //C
	    	  status=status+"0";
	      //V
	    	  status=status+"0";
	      //N
	      if(andi<0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      //S
	    	  status=status+"0";
	      //Z
	      if(andi==0)
	    	  status=status+"1";
	      else
	    	  status=status+"0";
	      break;
	  
	  case 6: 
		  Byte eor = (byte) (registers.get(rt) ^ registers.get(rs));
		  if(registers.get(rt)!=null)
		      registers.replace(rt, eor);
			  else
				  registers.put(rt,eor);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	    //C
    	  status=status+"0";
      //V
    	  status=status+"0";
      //N
      if(eor<0)
    	  status=status+"1";
      else
    	  status=status+"0";
      //S
    	  status=status+"0";
      //Z
      if(eor==0)
    	  status=status+"1";
      else
    	  status=status+"0";
	      break;
	  
	  case 7: 
		  String br = ensureEight(Integer.toBinaryString(registers.get(rt))) + "" + ensureEight(Integer.toBinaryString(registers.get(rs)));
	      pc= Short.parseShort(br,2);
	      tobedecoded=null;
	      tobeexecuted=null;
	      
	      status=status+"00000";
	      break;
	  
	  case 8: 
		  Byte nuSalbyte;
		  if(imm>31) {
				 imm=imm|0xFFFFFFC0;
				  nuSalbyte = (byte) imm;
			  }
			  else
				  nuSalbyte = (byte) imm;
		  Byte sal = (byte) (registers.get(rt) << nuSalbyte);
		  if(registers.get(rt)!=null)
		      registers.replace(rt, sal);
			  else
				  registers.put(rt,sal);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      //C
    	  status=status+"0";
      //V
    	  status=status+"0";
      //N
      if(sal<0)
    	  status=status+"1";
      else
    	  status=status+"0";
      //S
    	  status=status+"0";
      //Z
      if(sal==0)
    	  status=status+"1";
      else
    	  status=status+"0";
	      break;
	  
	  case 9: 
		  Byte nuSarbyte;
		  if(imm>31) {
				 imm=imm|0xFFFFFFC0;
				  nuSarbyte = (byte) imm;
			  }
			  else
				  nuSarbyte = (byte) imm;
		  Byte sar = (byte) (registers.get(rt) >> nuSarbyte);
		  if(registers.get(rt)!=null)
		      registers.replace(rt, sar);
			  else
				  registers.put(rt,sar);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      //C
    	  status=status+"0";
    	  //V
    	  status=status+"0";
    	  //N
    	  if(sar<0)
    		  status=status+"1";
    	  else
    		  status=status+"0";
    	  //S
    	  	status=status+"0";
    	  	//Z
    	  	if(sar==0)
    	  		status=status+"1";
    	  	else
    	  		status=status+"0";
	      break;
	      
	  case 10: 
		  Byte ldr = dataMemory[imm];
		  if(registers.get(rt)!=null)
		      registers.replace(rt, ldr);
			  else
				  registers.put(rt,ldr);
	      System.out.println("Target register  : "+rt +" 's value has been changed to "+registers.get(rt));
	      break;
	  
	  case 11: 
		  Byte str = registers.get(rt);
		  dataMemory[imm] = str;
		  System.out.println("Memory address  : "+imm +" 's value has been changed to "+str);
		  //nshoof mawdoo3 el 6 bits wel memory 11 bit
		  //size el register 8 bits? lw keda yb2a el shifting 3'alat
	      break;  
	  
	}
	if(registers.get(64)==null)
		registers.put(64, Byte.parseByte(status,2));
	else
		registers.replace(64, Byte.parseByte(status,2));
	tobeexecuted=null;
	System.out.println("Executing ended");
}

public static void main(String[] args){
	String testAssembly="CATest";
	try {
		readIntoArray(testAssembly);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	int cycle=1;
	while(pc<instructionMemory.length) {
	System.out.println("Clock Cycle number : "+cycle);
	if(tobeexecuted!=null)	
	execute(tobeexecuted);
	if(tobedecoded!=null)
	decode(tobedecoded);
	else
		System.out.println("No instruction has been decoded");
	fetch();
	System.out.println("_________________________________________________________________");
	cycle++;
	System.out.println("PC= "+pc);
	if(pc>1&&instructionMemory[pc-2]==null)
		break;
	}
	System.out.println("Content of the register file is ");
	Set<Integer> keys = registers.keySet();
    for(Integer key: keys) {
    	System.out.println("Register : R"+key+" has the value : "+registers.get(key));
    }
    System.out.println("_________________________________________________________________");
    System.out.println("Content of the Instruction memory is ");
    for(int i=0;i<instructionMemory.length;i++) {
    	if(instructionMemory[i]!=null)
    	System.out.println("Instruction "+(i+1)+" has the value : "+ensureSixteen(Integer.toBinaryString(instructionMemory[i])));
    }
    System.out.println("_________________________________________________________________");
    System.out.println("Content of the Data memory is "); 
    for(int i=0;i<dataMemory.length;i++) {
    	if(dataMemory[i]!=null)
    	System.out.println("Data at address "+(i)+" has the value : "+ensureEight(Integer.toBinaryString(dataMemory[i])));
    	}
    
}
}
    