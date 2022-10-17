import lejos.hardware.Button;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.chassis.Wheel;

public class MoteursRoues extends Ultrason {

	static int uneRotationRoue;
	private int distance;

	RegulatedMotor motorGauche = new EV3LargeRegulatedMotor(MotorPort.A);
	RegulatedMotor motorDroit = new EV3LargeRegulatedMotor(MotorPort.C);

	// mettre le diamètre des roues à la place des 1 dans modelWheel(motor, 1)
	Wheel wheel1 = WheeledChassis.modelWheel(motorGauche, 56).offset(60.5);
	Wheel wheel2 = WheeledChassis.modelWheel(motorDroit, 56).offset(-60.5);

	Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

	public MoteursRoues() {
		uneRotationRoue = this.getLimitAngle();
	}

	/**
	 * renvoie l'angle de rotation d'un moteur (je garde cette méthode sous le
	 * coude, je pense qu'elle peut être utile pour déterminer uneRotationRoue).
	 * 
	 * @return angle in degrees
	 */
	public int getLimitAngle() {
		return this.getLimitAngle();
	}

	public int getDistance() {
		return distance;
	}

	/**
	 * le robot avance de la distance d (ou avance avec la vitesse vitesse() ).
	 * 
	 * @param d la distance à parcourir.
	 */
	public void avance(int d) {
		// motorGauche.rotate(d, true);
		// motorDroit.rotate(d, true);
		// motorGauche.stop();
		// motorDroit.stop();

		chassis.travel((double) d);
		chassis.stop();
	}

	/**
	 * le robot recule de la distance d (ou recule à la vitesse vitesse() )
	 * 
	 * @param d la distance à parcourir
	 */
	public void recule(int d) {
		// motorGauche.rotate(-d, true);
		// motorDroit.rotate(-d, true);
		// motorGauche.setSpeed(d);
		// motorDroit.setSpeed(d);
		// motorGauche.backward();
		// motorDroit.backward();
		// motorGauche.stop();
		// motorDroit.stop();

		chassis.travel((double) -d);
		chassis.stop();

	}

	/**
	 * le robot s'arrete sauf si il y a un mur ou un palet. Dans ce cas-ci, il
	 * avance d'une rotation de roue (reste encore à déterminer).
	 * 
	 */
	public void arrete() {
		// motorGauche.stop();
		// motorDroit.stop();
		chassis.stop();
		if (!(estMur() || estPalet())) {
			avance(uneRotationRoue); // pas sur qu'une rotation suffise
		}
	}

	/**
	 * Le robot tourne à gauche. Si il n'y a pas de mur ou de robot, il avance d'une
	 * rotation de roue (reste encore à déterminer). Si il y a un mur, il s'arrete.
	 * Si il y a un robot qui arrive par la gauche, il tourne à droite.
	 * 
	 * @param angle de rotation du robot.
	 */
	public void tourneAGauche(int angle) {
		motorGauche.rotate(-angle, true);
		motorDroit.rotate(angle, true);
		if (!(estMur() || estRobot())) {
			avance(uneRotationRoue);
		} else if (estMur()) {
			arrete();
		} else if (estRobot()) { // et on détecte qu'il arrive à gauche ?
			tourneADroite(angle);
		}
	}

	/**
	 * Le robot tourne à droite. Si il n'y a pas de mur ou de robot, il avance d'une
	 * rotation de roue (reste encore à determiner). Si il y a un mur, il s'arrete.
	 * Si il y a un robot qui arrive par la droite, il tourne à gauche.
	 * 
	 * @param angle de rotation du robot.
	 */
	public void tourneADroite(int angle) {
		motorGauche.rotate(angle, true);
		motorDroit.rotate(-angle, true);
		if (!(estMur() || estRobot())) {
			avance(uneRotationRoue);
		} else if (estMur()) {
			arrete();
		} else if (estRobot()) { // et on détecte qu'il arrive à droite ?
			tourneAGauche(angle);
		}
	}

	/**
	 * détermine la vitesse du robot qu'il doit avoir. C'est la distance parcourue
	 * (ou que le robot doit parcourir) sur la durée maximale d'un parcours (soit 5
	 * min). Permet d'utiliser .setSpeed() sur les moteurs pour être plus précis.
	 * 
	 * @return la vitesse
	 */
	public int vitesse() {
		return distance / 300000;
	}

	public static void main(String[] args) {
		RegulatedMotor motorGauche = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor motorDroit = new EV3LargeRegulatedMotor(MotorPort.C);

		// mettre le diamètre des roues à la place des 1 dans modelWheel(motor, 1)
		Wheel wheel1 = WheeledChassis.modelWheel(motorGauche, 56).offset(60.5);
		Wheel wheel2 = WheeledChassis.modelWheel(motorDroit, 56).offset(-60.5);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

		chassis.travel(30.0);
		chassis.stop();
	}

}
