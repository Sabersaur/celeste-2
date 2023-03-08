public float[] get_line_intersection(int[] line1P1, int[] line1P2, int[] line2P1, int[] line2P2) {
  float[] result = new float[2];

  double s1_x = line1P2[x] - line1P1[x]; 
  double s1_y = line1P2[y] - line1P1[y];

  double s2_x = line2P2[x] - line2P1[x]; 
  double s2_y = line2P2[y] - line2P1[y]; 

  double s = (-s1_y * (line1P1[x] - line2P1[x]) + s1_x * (line1P1[y] - line2P1[y])) / (-s2_x * s1_y + s1_x * s2_y);
  double t = ( s2_x * (line1P1[y] - line2P1[y]) - s2_y * (line1P1[x] - line2P1[x])) / (-s2_x * s1_y + s1_x * s2_y);

  if (s >= 0 && s <= 1 && t >= 0 && t <= 1) { // Collision detected
    result[x] = (float) (line1P1[x] + (t * s1_x)); 
    result[y] = (float) (line1P1[y] + (t * s1_y));
  }

  return result;
}

final int x = 0;
final int y = 1;
float[] no;

int[] line1P1 = new int[2];
int[] line1P2 = new int[2];
int[] line2P1 = new int[2];
int[] line2P2 = new int[2];

void setup() {
  size(300,300);
  
  line1P1[x] = 100;
  line1P1[y] = 100;
  line2P1[x] = 200;
  line2P1[y] = 10;
  line2P2[x] = 200;
  line2P2[y] = 210;
}

void draw() {
  rect(-1,-1,500,500);
  line(line1P1[x], line1P1[y], line1P2[x], line1P2[y]);
  line(line2P1[x], line2P1[y], line2P2[x], line2P2[y]);
  no = get_line_intersection(line1P1, line1P2, line2P1, line2P2);
  ellipse(no[x], no[1], 10, 10);
}

void mouseClicked() {
  line1P2[x] = mouseX;
  line1P2[y] = mouseY;
}
