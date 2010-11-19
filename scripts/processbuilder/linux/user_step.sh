# This script will try to proceed with the next step of the launching mechanism, i.e.
# it will SUDO the command_step.
#
# If it fails, it will write an error message to the stderr and exit with error code 0.
# The error code however is not relevant, because the JAVA process launcher will interpret the
# error immediately, and ignore any follow-up

# parameters:
#	$1 - temp file for the return value of the user command
#	$2 - working dir for user command (absolute)
# 	$3 - user name
#	$4... - command to execute

# IMPORTANT: On error messages refer to the JavaDoc of OSProcessBuilder

# defining here the 'tokens' used in the java process builder
OSPL_E_PREFIX="_OS_PROCESS_LAUNCH_ERROR_";
OSPL_E_CAUSE="CAUSE";
OSLP_PACKAGE="org.objectweb.proactive.extensions.processbuilder.exception."
#---------------

# temp file
tmp=$1

# user name
usr=$3

# user command's working dir
workdir=$2

# simulating password input, in order not to get blocked in case "sudo -u root" happens for instance.
# also, we shift the first 3 parameters; this way $@ will contain only the user command
shift; shift; shift;

passw=$PA_OSPB_USER_PASSWORD
keycont=$PA_OSPB_USER_KEY_CONTENT

export PA_OSPB_USER_PASSWORD=***
export PA_OSPB_USER_KEY_CONTENT=***

if [ "$passw" == "" ]; then
  if [ "$keycont" == "" ]; then
    # use sudo
    echo 0 | sudo -ESHu $usr ./command_step.sh $tmp "$workdir" "$@";
    exitc=$?
  else
    # use ssh
    export >> $tmp;

    keyfile=`mktemp`
    echo "$keycont" > $keyfile
    chmod 400 $keyfile

    for i in "$@"
      do
      args="${args} ""'"${i//\'/\'\"\'\"\'}"'"
    done
    ssh -n -i $keyfile $usr@localhost `pwd`/command_step.sh $tmp "$workdir" $args
    exitc=$?
    rm $keyfile
  fi;
else
  # if we use the 'suer' than since it will write the command to the shell as text, we have to make
  # sure that our command will always be wellformed. Thus, we write it out to environment variables
  # and pass their names to the executable. We actually pass the environment variables complete with
  # dollar sign, so when they get written to the bash, they will be replaced with their values automatically
  env_var_prefix="PA_OSPB_CMD_ARG_NO_"
  cnt=1
  for i in "$@"
  do
    export ${env_var_prefix}${cnt}="$i"
    args="${args} "\"""\$"$env_var_prefix$cnt"\"
    cnt=`expr $cnt + 1`
  done

  # check if we are running on a 64bit arch, or a 32bit one.
  # The only difference between the 'suer' executables is their
  # target architecture used at compilation time.
  if [[ `uname -i` == *64* ]];
  then
    echo "$passw" | ./suer64 $usr ./command_step.sh $tmp "$workdir" $args;
    exitc=$?
  else
    echo "$passw" | ./suer32 $usr ./command_step.sh $tmp "$workdir" $args;
    exitc=$?
  fi
  ###### DEVELOPER NOTE:
  #	In case the 'suer' solution does not meet all requirements, it is possible to conveniently replace
  #	it with a solution built on the Expect library. We need just a script which could interpret the 'su'
  #	messages. Since expect mixes together the error and output channels, a named pipe should be used to
  #	forward the error of the inner commands to the outside world. This named pipe could be created in the
  #	temp directory, and deleted when the scripts exit.
  ######
fi;

# sudo should exit with error code 1 only in case it was unsuccessful
# the command which is executed inside will pass its return value through a tempfile
if [ $exitc != 0 ]; then
  error="$OSPL_E_PREFIX ${OSLP_PACKAGE}OSUserException $OSPL_E_CAUSE Cannot execute as user $usr! (Code=$exitc)";
  echo $error 1>&2;
  exit 1;
fi;

